package com.hbworld.likhit.view.detailView

import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.domain.usecase.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase
) : BaseViewModel<DetailScreenUiState, DetailScreenUiEvent>() {

    private val _state = MutableStateFlow<DetailScreenUiState>(DetailScreenUiState.Loading)
    val state: StateFlow<DetailScreenUiState> = _state.asStateFlow()


    fun onEvent(event: DetailScreenUiEvent) {
        when (event) {
            DetailScreenUiEvent.OnBackClick -> handleOnBackClick()
            DetailScreenUiEvent.OnEditClick -> handleOnEditClick()
            is DetailScreenUiEvent.OnFirstComposition -> handleOnFirstComposition(event.noteId)
        }
    }

    private fun handleOnFirstComposition(noteId: Long) {
        viewModelScope.launch {
            try {
                val note = getNoteUseCase.getNoteById(noteId)
                _state.emit(DetailScreenUiState.Success(note.title, note.description))
            } catch (e: Exception) {
                _state.emit(DetailScreenUiState.Error(e.message ?: "Unknown error"))
            }
        }
    }

    private fun handleOnEditClick() {
        TODO("Not yet implemented")
    }

    private fun handleOnBackClick() {
        TODO("Not yet implemented")
    }
}