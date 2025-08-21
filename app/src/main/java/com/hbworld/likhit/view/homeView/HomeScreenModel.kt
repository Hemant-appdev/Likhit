package com.hbworld.likhit.view.homeView

import com.hbworld.likhit.base.UIEvent
import com.hbworld.likhit.base.UIState
import com.hbworld.likhit.data.local.Note

sealed class HomeScreenUiState() : UIState {
    object Loading : HomeScreenUiState()
    data class Error(val errorMessage: String) : HomeScreenUiState()
    data class Data(val notes: List<Note>) : HomeScreenUiState()

    companion object{
        fun initialData() = Data(emptyList())
    }
}

sealed class HomeScreenUiEvent() : UIEvent {
    object OnAddNewClick : HomeScreenUiEvent()
}