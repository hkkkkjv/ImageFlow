package ru.kpfu.itis.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import ru.kpfu.itis.core.di.qualifiers.ApplicationContext
import ru.kpfu.itis.data.settings.settingsDataStore
import javax.inject.Singleton

@Module
class DataStoreModule {
    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        context.settingsDataStore
}