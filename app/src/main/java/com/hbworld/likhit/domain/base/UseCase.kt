package com.hbworld.likhit.domain.base

/**
 * Base interface for a use case that performs a suspended one-shot operation.
 * @param P The type of the parameters.
 * @param R The return type.
 */
interface SuspendUseCase<in P, out R> {
    suspend operator fun invoke(
        params: P,
        retryCount: Int = 0,
        initialDelayMillis: Long = 0L
    ): Result<R>
}