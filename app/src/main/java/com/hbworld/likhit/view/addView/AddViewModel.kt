package com.hbworld.likhit.view.addView

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase
) : BaseViewModel<AddScreenUiState, AddScreenUiEvent>() {

    private val _state = MutableStateFlow(AddScreenUiState.State.initialState())
    val state: StateFlow<AddScreenUiState.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AddScreenUiEffect>()
    val effect: SharedFlow<AddScreenUiEffect> = _effect.asSharedFlow()

    fun onViewEvent(event: AddScreenUiEvent) {
        when (event) {
            AddScreenUiEvent.OnBackClick -> handleOnBackClick()
            is AddScreenUiEvent.OnSaveClick -> handleOnSaveClick()
            is AddScreenUiEvent.OnDescriptionChange -> handleOnDescriptionChange(event.description)
            is AddScreenUiEvent.OnTitleChange -> handleOnTitleChange(event.title)
        }
    }

    private fun handleOnTitleChange(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    private fun handleOnDescriptionChange(description: String) {
        _state.update {
            it.copy(description = description)
        }
    }

    private fun handleOnSaveClick() {
        if (state.value.isLoading == true) return
        if (state.value.title.isBlank() || state.value.description.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val id = addNoteUseCase.addNote(state.value.title, state.value.description)
                _effect.emit(AddScreenUiEffect.ShowToastAndNavigateBack("Note saved successfully!"))
                Log.d("AddViewModel", "save success with id -> $id")
            } catch (e: Exception) {
                _effect.emit(AddScreenUiEffect.ShowToast("Couldn't save the note"))
                Log.e("AddViewModel", "error while saving note -> ${e.message}")
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun handleOnBackClick() {
        viewModelScope.launch {
            _effect.emit(AddScreenUiEffect.NavigateBack)
        }
    }
}