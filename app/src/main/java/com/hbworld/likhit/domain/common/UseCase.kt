package com.hbworld.likhit.domain.common

import com.hbworld.likhit.domain.common.model.Result
import kotlinx.coroutines.flow.Flow

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

interface FlowableUseCase<in P, out R> {
    operator fun invoke(
        params: P,
        retryCount: Long = 1L
    ): Flow<Result<R>>
}