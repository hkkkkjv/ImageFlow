package ru.kpfu.itis.data.mapper

import retrofit2.HttpException
import ru.kpfu.itis.domain.common.DomainError
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal fun Throwable.toDomainError(): DomainError = when (this) {
    is UnknownHostException -> DomainError.Network.NoConnection
    is SocketTimeoutException -> DomainError.Network.Timeout
    is HttpException -> DomainError.Network.Server(code())
    is IOException -> DomainError.Network.NoConnection
    else -> DomainError.Unknown
}