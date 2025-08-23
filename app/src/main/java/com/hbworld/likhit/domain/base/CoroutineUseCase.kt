package com.hbworld.likhit.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.repeat

/**
 * 
 */
abstract class CoroutineUseCase<in P, out R>(
    private val coroutineDispatcher: CoroutineDispatcher
) : SuspendUseCase<P, R> {

    override suspend operator fun invoke(
        params: P,
        retryCount: Int,
        initialDelayMillis: Long
    ): Result<R> {
        return withContext(coroutineDispatcher) {
            var currentDelay = initialDelayMillis
            repeat(retryCount + 1) { attempt ->
                try {
                    return@withContext Result.Success(execute(params))
                } catch (e: Exception) {
                    if (attempt == retryCount) {
                        return@withContext Result.Error(e)
                    } else {
                        delay(currentDelay)
                        currentDelay *= 2
                    }
                }
            }
            Result.Error(Exception("Illegal State Exception"))
        }
    }

    protected abstract suspend fun execute(params: P): R
}