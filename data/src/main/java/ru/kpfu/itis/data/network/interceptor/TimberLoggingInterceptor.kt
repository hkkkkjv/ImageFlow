package ru.kpfu.itis.data.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

object TimberLoggingInterceptor {
    fun create(level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.apply {
            setLevel(level)
        }
    }
}