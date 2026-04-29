package ru.kpfu.itis.data.network

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import retrofit2.HttpException
import timber.log.Timber

suspend fun <T> retryWithExponentialBackoff(
    maxRetries: Int = 3,
    initialDelayMs: Long = 1000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMs
    repeat(maxRetries) { attempt ->
        try {
            return block()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            if (e is HttpException && e.code() < 500) throw e
            if (attempt == maxRetries - 1) throw e
            Timber.w("Retry ${attempt + 1}/$maxRetries after ${currentDelay}ms: ${e.message}")
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong()
        }
    }
    throw IllegalStateException("Retry loop completed without success or exception")
}