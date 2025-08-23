package com.hbworld.likhit.ui.view.detailView

import com.hbworld.likhit.ui.view.common.BaseViewModel
import com.hbworld.likhit.domain.usecase.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase
) : BaseViewModel<DetailScreenUiState, DetailScreenUiEvent, DetailScreenUiEffect>() {

    override fun createInitialState() = DetailScreenUiState.Loading

    override fun handleEvent(event: DetailScreenUiEvent) {
        when (event) {
            DetailScreenUiEvent.OnBackClick -> handleOnBackClick()
            DetailScreenUiEvent.OnEditClick -> handleOnEditClick()
            is DetailScreenUiEvent.OnFirstComposition -> handleOnFirstComposition(event.noteId)
        }
    }

    private fun handleOnFirstComposition(noteId: Long) {
        executeSuspendUseCase(
            useCase = getNoteUseCase,
            param = GetNoteUseCase.Param(noteId),
            onError = { e ->
                setState { DetailScreenUiState.Error(e.message.toString()) }
            },
            onSuccess = { data ->
                setState { DetailScreenUiState.Success(data.title, data.description) }
            }
        )
    }

    private fun handleOnEditClick() {
        TODO("Not yet implemented")
    }

    private fun handleOnBackClick() {
        TODO("Not yet implemented")
    }
}