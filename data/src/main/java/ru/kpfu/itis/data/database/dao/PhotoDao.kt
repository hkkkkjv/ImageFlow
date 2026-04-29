package ru.kpfu.itis.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.data.database.entity.PhotoEntity

@Dao
interface PhotoDao {
    @Query("SELECT COUNT(*) > 0 FROM photos")
    suspend fun hasCache(): Boolean

    @Query("SELECT * FROM photos")
    suspend fun getCachedPhotos(): List<PhotoEntity>

    @Query("SELECT * FROM photos ORDER BY liked DESC, downloadedAt DESC")
    fun observeAll(): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getPhotoById(id: String): PhotoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(photo: PhotoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAll(photos: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(photos: List<PhotoEntity>) {
        clearAll()
        insertOrUpdateAll(photos)
    }
}