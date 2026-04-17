package ru.kpfu.itis.imageflow.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.kpfu.itis.core.provider.ApiKeyProvider
import ru.kpfu.itis.imageflow.BuildConfig

@Module
class AppModule(private val application: android.app.Application) {

    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideApiKey(): String = BuildConfig.PEXELS_API_KEY

    @Provides
    fun provideApiKeyProvider(apiKey: String): ApiKeyProvider = ApiKeyProvider(apiKey)
}