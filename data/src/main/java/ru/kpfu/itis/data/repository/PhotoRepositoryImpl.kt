package ru.kpfu.itis.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import ru.kpfu.itis.core.di.qualifiers.coroutines.IODispatcher
import ru.kpfu.itis.data.database.dao.PhotoDao
import ru.kpfu.itis.data.mapper.toDomain
import ru.kpfu.itis.data.mapper.toDomainError
import ru.kpfu.itis.data.mapper.toDomainList
import ru.kpfu.itis.data.mapper.toDomainListFromCache
import ru.kpfu.itis.data.mapper.toEntityList
import ru.kpfu.itis.data.network.PexelsApi
import ru.kpfu.itis.data.network.retryWithExponentialBackoff
import ru.kpfu.itis.data.settings.ImageQuality
import ru.kpfu.itis.data.settings.SettingsManager
import ru.kpfu.itis.domain.common.DomainError
import ru.kpfu.itis.domain.common.Result
import ru.kpfu.itis.domain.model.Photo
import ru.kpfu.itis.domain.model.PhotoId
import ru.kpfu.itis.domain.repository.PhotoRepository
import timber.log.Timber
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: PexelsApi,
    private val dao: PhotoDao,
    private val settingsManager: SettingsManager,
    @param:IODispatcher private val ioDispatcher: CoroutineDispatcher
) : PhotoRepository {

    private val dbMutex = Mutex()
    private val downloadChannel = Channel<Photo>(capacity = Channel.UNLIMITED)
    private val repositoryScope = CoroutineScope(SupervisorJob() + ioDispatcher)

    init {
        startDownloadWorker()
    }

    fun observePhotosOfflineFirst(query: String): Flow<List<Photo>> =
        flow {
            dao.observeAll().map { it.toDomainListFromCache() }.collect { cached ->
                emit(cached)

                if (cached.isEmpty()) {
                    CoroutineScope(ioDispatcher).launch {
                        loadPhotosFromNetwork(query, ImageQuality.ORIGINAL)
                    }
                }
            }
        }.flowOn(ioDispatcher)

    override suspend fun searchPhotos(query: String, page: Int): Result<List<Photo>> =
        loadPhotosFromNetwork(query, ImageQuality.ORIGINAL, page)

    private suspend fun loadPhotosFromNetwork(
        query: String,
        quality: ImageQuality,
        page: Int = 1
    ): Result<List<Photo>> = withContext(ioDispatcher) {
        return@withContext try {
            val response = retryWithExponentialBackoff {
                api.searchPhotos(query, page)
            }
            val domainPhotos = response.photos.toDomainList()

            dbMutex.withLock {
                dao.insertOrUpdateAll(response.photos.toEntityList())
            }
            Result.Success(domainPhotos)
        } catch (e: Throwable) {
            Timber.e(e, "Network load failed for query: $query")
            Result.Error(e.toDomainError())
        }
    }

    override fun observeFavorites(): Flow<List<Photo>> =
        dao.observeAll().map { list ->
            list.filter { it.liked }.toDomainListFromCache()
        }.flowOn(ioDispatcher)

    override suspend fun toggleLike(photoId: PhotoId): Result<Photo> = withContext(ioDispatcher) {
        dbMutex.withLock {
            val entity = dao.getPhotoById(photoId.value) ?: return@withContext Result.Error(
                DomainError.Unknown
            )
            val updated = entity.copy(liked = !entity.liked)
            dao.insertOrUpdate(updated)
            Result.Success(updated.toDomain())
        }
    }

    private fun startDownloadWorker() = repositoryScope.launch {
        for (photo in downloadChannel) {
            Timber.d("Background download started for ${photo.id}")
            delay(500)
        }
    }

    suspend fun enqueueDownload(photo: Photo) {
        downloadChannel.send(photo)
    }

    fun clear() {
        downloadChannel.close()
        repositoryScope.cancel()
    }
}