package com.hbworld.likhit.domain.common

import com.hbworld.likhit.domain.common.model.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen

abstract class FlowUseCase<in P, out R>(
    private val coroutineDispatcher: CoroutineDispatcher
) : FlowableUseCase<P, R> {

    override operator fun invoke(params: P, retryCount: Long): Flow<Result<R>> =
        execute(params)
            .map<R, Result<R>> { Result.Success(it) }
            .retryWhen { cause, attempt ->
                if (cause as Exception is CancellationException) return@retryWhen false
                if (attempt < retryCount) {
                    delay(1000L * attempt * 2)
                    return@retryWhen true
                } else {
                    return@retryWhen false
                }
            }
            .catch { cause ->
                emit(Result.Error(cause as Exception))
            }
            .flowOn(coroutineDispatcher)

    protected abstract fun execute(params: P): Flow<R>
}