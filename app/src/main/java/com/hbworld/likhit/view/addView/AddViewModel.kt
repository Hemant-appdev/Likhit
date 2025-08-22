package com.hbworld.likhit.view.addView

import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    val addNoteUseCase: AddNoteUseCase
) : BaseViewModel<AddScreenUiState, AddScreenUiEvent>() {

    private val _state = MutableStateFlow(AddScreenUiState.State.initialState())
    val state: StateFlow<AddScreenUiState.State> =  _state.asStateFlow()

    fun onViewEvent(event: AddScreenUiEvent) {
        when (event) {
            AddScreenUiEvent.OnBackClick -> handleOnBackClick()
            is AddScreenUiEvent.OnSaveClick -> handleOnSaveClick()
            is AddScreenUiEvent.OnDescriptionChange -> handleOnDescriptionChange(event.description)
            is AddScreenUiEvent.OnTitleChange -> handleOnTitleChange(event.title)
        }
    }

    private fun handleOnTitleChange(title: String) {
        _state.update { crr ->
            crr.copy(title = title)
        }
    }

    private fun handleOnDescriptionChange(description: String) {
        _state.update { crr ->
            crr.copy(description = description)
        }
    }

    private fun handleOnSaveClick() {
        viewModelScope.launch {
            try {
                val id = addNoteUseCase.addNote(state.value.title, state.value.description)
                println("save success with id -> $id")
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun handleOnBackClick() {

    }

}