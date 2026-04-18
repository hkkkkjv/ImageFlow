package ru.kpfu.itis.domain.common

sealed interface DomainError {
    sealed interface Network : DomainError {
        data object NoConnection : Network
        data object Timeout : Network
        data class Server(val httpCode: Int) : Network
    }
    sealed interface Auth : DomainError {
        data object Unauthorized : Auth
    }
    data object Unknown : DomainError
}