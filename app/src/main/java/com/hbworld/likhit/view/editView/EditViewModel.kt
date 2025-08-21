package com.hbworld.likhit.view.editView

import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    val addNoteUseCase: AddNoteUseCase
) : BaseViewModel<EditScreenUIState, EditScreenUIEvent>() {

    private val _state: MutableStateFlow<EditScreenUIState.State> =
        MutableStateFlow(EditScreenUIState.State.initialState())

    val state: StateFlow<EditScreenUIState.State>
        get() = _state

    fun onViewEvent(event: EditScreenUIEvent) {
        when (event) {
            EditScreenUIEvent.OnBackClick -> handleOnBackClick()
            is EditScreenUIEvent.OnSaveClick -> handleOnSaveClick()
            is EditScreenUIEvent.OnDescriptionChange -> handleOnDescriptionChange(event.description)
            is EditScreenUIEvent.OnTitleChange -> handleOnTitleChange(event.title)
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
                val id = addNoteUseCase.saveNote(state.value.title, state.value.description)
                println("save success with id -> $id")
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun handleOnBackClick() {

    }

}