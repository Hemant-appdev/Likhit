package com.hbworld.likhit.ui.view.homeView

import android.util.Log
import com.hbworld.likhit.ui.view.common.BaseViewModel
import com.hbworld.likhit.domain.usecase.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllNotesUseCase: GetAllNotesUseCase
) : BaseViewModel<HomeScreenUiState, HomeScreenUiEvent, HomeScreenUiEffect>() {

    override fun createInitialState() = HomeScreenUiState.Loading

    override fun handleEvent(event: HomeScreenUiEvent) {
        when (event) {
            HomeScreenUiEvent.OnAddNewClick -> handleOnAddNewClick()
            is HomeScreenUiEvent.OnNoteClick -> handleOnNoteClick(event.id)
        }
    }

    private fun handleOnNoteClick(id: Long) {
        setEffect { HomeScreenUiEffect.NavigateToDetailScreen(id) }
    }

    private fun handleOnAddNewClick() {
        setEffect { HomeScreenUiEffect.NavigateToAddScreen }
    }

//    override val uiState: StateFlow<HomeScreenUiState> = getAllNotesUseCase.getAllNotes()
//        .map<List<NoteEntity>, HomeScreenUiState> { notes ->
//            HomeScreenUiState.Data(notes)
//        }
//        .onStart {
//            Log.d("HomeViewModel", "getAll Note onStart")
//        }
//        .onCompletion {
//            Log.d("HomeViewModel", "getAll Note onCompletion")
//        }
//        .catch { e ->
//            Log.e("HomeViewModel", e.message.toString())
//            emit(HomeScreenUiState.Error(e.message.toString()))
//        }
//        .onEach { it ->
//            Log.d("HomeViewModel", "getAll Note onEach -> $it")
//        }
//        .stateIn(
//            scope = viewModelScope,
//            initialValue = HomeScreenUiState.Loading,
//            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L)
//        )

    override val uiState: StateFlow<HomeScreenUiState> = collectInState(
        useCase = getAllNotesUseCase,
        params = Unit,
        initialValue = HomeScreenUiState.Loading,
        onError = { e ->
            Log.e("HomeViewModel", e.message.toString())
            HomeScreenUiState.Error(e.message.toString())
        },
        onSuccess = { state ->
            HomeScreenUiState.Data(state)
        }
    )
}