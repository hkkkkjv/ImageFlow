package ru.kpfu.itis.domain.common

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val error: DomainError) : Result<Nothing>
    data object Loading : Result<Nothing>
}

inline fun <T, R> Result<T>.fold(
    onSuccess: (T) -> R,
    onError: (DomainError) -> R,
    onLoading: () -> R
): R = when (this) {
    is Result.Success -> onSuccess(data)
    is Result.Error -> onError(error)
    Result.Loading -> onLoading()
}