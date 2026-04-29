package ru.kpfu.itis.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class SettingsManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {


    val settingsFlow: Flow<AppSettings> = dataStore.data.map { preferences ->
        AppSettings(
            themeMode = preferences[THEME_KEY].toEnum(ThemeMode.SYSTEM),
            imageQuality = preferences[IMAGE_KEY].toEnum(ImageQuality.ORIGINAL),
            cacheLimit = preferences[CACHE_KEY] ?: 100
        )
    }

    suspend fun updateTheme(mode: ThemeMode) {
        dataStore.edit { it[THEME_KEY] = mode.name }
    }

    suspend fun updateImageQuality(quality: ImageQuality) {
        dataStore.edit { it[IMAGE_KEY] = quality.name }
    }

    suspend fun updateCacheLimit(limit: Int) {
        dataStore.edit { it[CACHE_KEY] = limit }
    }

    companion object {
        private val THEME_KEY = stringPreferencesKey("theme_mode")
        private val IMAGE_KEY = stringPreferencesKey("image_quality")
        private val CACHE_KEY = intPreferencesKey("cache_limit")
    }
}

private inline fun <reified T : Enum<T>> String?.toEnum(default: T): T =
    this?.let { enumValues<T>().firstOrNull { it.name == this } } ?: default