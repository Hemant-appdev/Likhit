package com.hbworld.likhit.view.homeView

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.domain.usecase.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllNotesUseCase: GetAllNotesUseCase
) : BaseViewModel<HomeScreenUiState, HomeScreenUiEvent>() {

    private val _effect = MutableSharedFlow<HomeScreenUiEffect>()
    val effect: Flow<HomeScreenUiEffect> = _effect.asSharedFlow()

    fun onViewEvent(event: HomeScreenUiEvent) {
        when (event) {
            HomeScreenUiEvent.OnAddNewClick -> handleOnAddNewClick()
            is HomeScreenUiEvent.OnNoteClick -> handleOnNoteClick(event.id)
        }
    }

    private fun handleOnNoteClick(id: Long) {
        viewModelScope.launch {
            _effect.emit(HomeScreenUiEffect.NavigateToDetailScreen(id))
        }
    }

    private fun handleOnAddNewClick() {
        viewModelScope.launch {
            _effect.emit(HomeScreenUiEffect.NavigateToAddScreen)
        }
    }

    val state: StateFlow<HomeScreenUiState> = getAllNotesUseCase.getAllNotes()
        .map<List<Note>, HomeScreenUiState> { notes ->
            HomeScreenUiState.Data(notes)
        }
        .onStart {
            Log.d("HomeViewModel", "getAll Note onStart")
        }
        .onCompletion {
            Log.d("HomeViewModel", "getAll Note onCompletion")
        }
        .catch { e ->
            Log.e("HomeViewModel", e.message.toString())
            emit(HomeScreenUiState.Error(e.message.toString()))
        }
        .onEach { it ->
            Log.d("HomeViewModel", "getAll Note onEach -> $it")
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = HomeScreenUiState.Loading,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L)
        )
}