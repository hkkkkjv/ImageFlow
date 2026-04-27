package ru.kpfu.itis.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import ru.kpfu.itis.data.database.DatabaseConfig
import ru.kpfu.itis.data.database.PhotoDatabase
import ru.kpfu.itis.data.database.dao.PhotoDao
import timber.log.Timber
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providePhotoDatabase(context: Context): PhotoDatabase =
        Room.databaseBuilder(context, PhotoDatabase::class.java, DatabaseConfig.NAME)
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    Timber.d("📦 Database created: ${DatabaseConfig.NAME}")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.execSQL("PRAGMA synchronous=NORMAL")
                    db.execSQL("PRAGMA journal_mode=WAL")
                    db.execSQL("PRAGMA cache_size=-2000")
                }
            })
            .fallbackToDestructiveMigration(dropAllTables = false)
            .build()

    @Provides
    @Singleton
    fun providePhotoDao(database: PhotoDatabase): PhotoDao = database.photoDao()
}