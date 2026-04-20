package ru.kpfu.itis.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.kpfu.itis.core.provider.ApiKeyProvider
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(
    private val apiKeyProvider: ApiKeyProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", apiKeyProvider.getApiKey())
            .build()
        return chain.proceed(request)
    }
}