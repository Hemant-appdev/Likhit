package com.hbworld.likhit.view.detailView

import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.domain.base.Result
import com.hbworld.likhit.domain.usecase.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            val result = getNoteUseCase(params = GetNoteUseCase.Param(noteId))
            when (result) {
                is Result.Error -> {
                    setState { DetailScreenUiState.Error(result.exception.message.toString()) }
                }

                is Result.Success<Note> -> {
                    setState { DetailScreenUiState.Success(result.data.title, result.data.description) }
                }
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