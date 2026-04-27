package ru.kpfu.itis.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kpfu.itis.data.database.converter.ListTypeConverter
import ru.kpfu.itis.data.database.dao.PhotoDao
import ru.kpfu.itis.data.database.entity.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = DatabaseConfig.VERSION,
    exportSchema = true
)
@TypeConverters(ListTypeConverter::class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}