package com.hbworld.likhit.ui.view.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.domain.common.FlowableUseCase
import com.hbworld.likhit.domain.common.SuspendUseCase
import com.hbworld.likhit.domain.common.model.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : UIState, E : UIEvent, F : UIEffect>() :
    ViewModel() {

    protected abstract fun createInitialState(): S

    private val _uiState by lazy { MutableStateFlow(createInitialState()) }
    open val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<F>()
    val effect: SharedFlow<F> = _effect.asSharedFlow()


    protected fun setState(reducer: S.() -> S) {
        _uiState.update(reducer)
    }

    protected fun setEffect(builder: () -> F) {
        viewModelScope.launch {
            _effect.emit(builder())
        }
    }

    fun onEvent(event: E) {
        handleEvent(event)
    }

    protected abstract fun handleEvent(event: E)


    /**
     * Executes a one-shot suspend use case and handles the result.
     * @param useCase The use case to be executed.
     * @param params The parameters for the use case.
     * @param onLoading A lambda to update the state to loading.
     * @param onSuccess A lambda to handle the successful result.
     * @param onError A lambda to handle the error result.
     */
    protected fun <P, R> executeSuspendUseCase(
        useCase: SuspendUseCase<P, R>,
        param: P,
        retryCount: Int = 0,
        initialDelayMillis: Long = 0L,
        onLoading: () -> Unit = {},
        onError: (Exception) -> Unit,
        onSuccess: (R) -> Unit
    ) {
        onLoading()
        viewModelScope.launch {
            when (val result = useCase(
                params = param,
                retryCount = retryCount,
                initialDelayMillis = initialDelayMillis
            )) {
                is Result.Error -> onError(result.exception)
                is Result.Success<R> -> onSuccess(result.data)
            }
        }
    }

    protected fun <P, R> collectInState(
        useCase: FlowableUseCase<P, R>,
        params: P,
        initialValue: S,
        retryCount: Long = 3L,
        onStart: () -> Unit = {},
        onCompletion: (Throwable?) -> Unit = {},
        onError: (Exception) -> S,
        onSuccess: (R) -> S
    ): StateFlow<S> {
        return useCase(params = params, retryCount = retryCount)
            .onStart { onStart() }
            .map { result ->
                when (result) {
                    is Result.Error -> onError(result.exception)
                    is Result.Success<R> -> onSuccess(result.data)
                }
            }
            .onCompletion { throwable ->
                onCompletion(throwable)
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = initialValue,
                started = SharingStarted.Companion.WhileSubscribed(5000L)
            )
    }
}