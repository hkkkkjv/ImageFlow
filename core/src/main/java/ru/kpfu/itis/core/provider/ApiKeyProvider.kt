package ru.kpfu.itis.core.provider

class ApiKeyProvider(private val apiKey: String) {
    fun getApiKey(): String = apiKey

    fun isValid(): Boolean = apiKey.isNotBlank() && apiKey.length >= 32
}