package ru.kpfu.itis.imageflow.di

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import ru.kpfu.itis.core.di.qualifiers.ApplicationContext
import ru.kpfu.itis.core.di.qualifiers.coroutines.DefaultDispatcher
import ru.kpfu.itis.core.di.qualifiers.coroutines.IODispatcher
import ru.kpfu.itis.core.di.qualifiers.coroutines.MainDispatcher
import ru.kpfu.itis.core.provider.ApiKeyProvider
import ru.kpfu.itis.imageflow.BuildConfig
import javax.inject.Singleton

@Module
class AppModule(private val application: android.app.Application) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application.applicationContext

    @Provides
    fun provideApiKey(): String = BuildConfig.PEXELS_API_KEY

    @Provides
    fun provideApiKeyProvider(apiKey: String): ApiKeyProvider = ApiKeyProvider(apiKey)

    @Provides
    @Singleton
    @DefaultDispatcher
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Provides
    @Singleton
    @IODispatcher
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    @MainDispatcher
    fun provideMainDispatcher() = Dispatchers.Main
}