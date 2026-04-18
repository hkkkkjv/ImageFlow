package ru.kpfu.itis.domain.usecase

interface UseCase<in Params, out T> {
    suspend operator fun invoke(params: Params): Result<T>
}