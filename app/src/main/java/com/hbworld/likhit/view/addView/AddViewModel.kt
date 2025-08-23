package com.hbworld.likhit.view.addView

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.domain.usecase.AddNoteUseCase
import com.hbworld.likhit.domain.base.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        // 4. Use the `setState` helper for cleaner updates
        setState { copy(title = title) }
    }

    private fun handleOnDescriptionChange(description: String) {
        setState { copy(description = description) }
    }

    private fun handleOnSaveClick() {
        if (uiState.value.isLoading == true) return
        if (uiState.value.title.isBlank() || uiState.value.description.isBlank()) return

        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val result = addNoteUseCase(
                retryCount = 2,
                params = AddNoteUseCase.Param(
                    title = uiState.value.title,
                    description = uiState.value.description
                )
            )
            when (result) {
                is Result.Success<Long> -> {
                    setEffect { AddScreenUiEffect.ShowToastAndNavigateBack("Note saved successfully!") }
                    Log.d("AddViewModel", "save success with id ->${result.data}")
                }

                is Result.Error -> {
                    setEffect { AddScreenUiEffect.ShowToast("Couldn't save the note") }
                    Log.e("AddViewModel", "error while saving note -> ${result.exception}")
                }
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun handleOnBackClick() {
        setEffect { AddScreenUiEffect.NavigateBack }
    }
}