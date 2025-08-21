package com.hbworld.likhit.view.homeView

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hbworld.likhit.base.BaseViewModel
import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.domain.usecase.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllNotesUseCase: GetAllNotesUseCase
) : BaseViewModel<HomeScreenUiState, HomeScreenUiEvent>() {

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