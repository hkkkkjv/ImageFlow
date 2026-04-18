package ru.kpfu.itis.domain.usecase

abstract class NoParamsUseCase<out T> : UseCase<Unit, T> {
    final override suspend fun invoke(params: Unit): Result<T> = execute()
    protected abstract suspend fun execute(): Result<T>
}