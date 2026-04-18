package ru.kpfu.itis.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.domain.model.Photo
import ru.kpfu.itis.domain.model.PhotoId

interface PhotoRepository {
    suspend fun searchPhotos(query: String, page: Int = 1): Result<List<Photo>>
    fun observeFavorites(): Flow<List<Photo>>
    suspend fun toggleLike(photoId: PhotoId): Result<Photo>
}