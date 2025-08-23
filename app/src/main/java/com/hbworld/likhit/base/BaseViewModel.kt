package com.hbworld.likhit.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.domain.base.Result
import com.hbworld.likhit.domain.base.SuspendUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
        onLoading: () -> Unit = {},
        onError: (Exception) -> Unit,
        onSuccess: (R) -> Unit
    ) {
        onLoading()
        viewModelScope.launch {
            when (val result = useCase(param)) {
                is Result.Error -> onError(result.exception)
                is Result.Success<R> -> onSuccess(result.data)
            }
        }
    }
}