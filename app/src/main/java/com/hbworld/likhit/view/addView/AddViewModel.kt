package com.hbworld.likhit.view.addView

import android.util.Log
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase
) : BaseViewModel<AddScreenUiState.State, AddScreenUiEvent, AddScreenUiEffect>() {

    override fun createInitialState() = AddScreenUiState.State.initialState()

    override fun handleEvent(event: AddScreenUiEvent) {
        when (event) {
            AddScreenUiEvent.OnBackClick -> handleOnBackClick()
            is AddScreenUiEvent.OnSaveClick -> handleOnSaveClick()
            is AddScreenUiEvent.OnDescriptionChange -> handleOnDescriptionChange(event.description)
            is AddScreenUiEvent.OnTitleChange -> handleOnTitleChange(event.title)
        }
    }

    private fun handleOnTitleChange(title: String) {
        setState { copy(title = title) }
    }

    private fun handleOnDescriptionChange(description: String) {
        setState { copy(description = description) }
    }

    private fun handleOnSaveClick() {
        if (uiState.value.isLoading == true) return
        if (uiState.value.title.isBlank() || uiState.value.description.isBlank()) return

        executeSuspendUseCase(
            useCase = addNoteUseCase,
            param = AddNoteUseCase.Param(
                title = uiState.value.title,
                description = uiState.value.description
            ),
            onLoading = { setState { copy(isLoading = true) } },
            onError = { e ->
                setState { copy(isLoading = false) }
                setEffect { AddScreenUiEffect.ShowToast("Couldn't save the note") }
                Log.e("AddViewModel", "error while saving note -> ${e.message}")
            },
            onSuccess = { data ->
                setState { copy(isLoading = false) }
                setEffect { AddScreenUiEffect.ShowToastAndNavigateBack("Note saved successfully!") }
                Log.d("AddViewModel", "save success with id ->${data}")
            }
        )
    }

    private fun handleOnBackClick() {
        setEffect { AddScreenUiEffect.NavigateBack }
    }
}