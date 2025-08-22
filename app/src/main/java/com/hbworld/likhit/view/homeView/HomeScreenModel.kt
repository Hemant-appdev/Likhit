package com.hbworld.likhit.view.homeView

import com.hbworld.likhit.base.UIEffect
import com.hbworld.likhit.base.UIEvent
import com.hbworld.likhit.base.UIState
import com.hbworld.likhit.data.local.Note

sealed class HomeScreenUiState() : UIState {
    object Loading : HomeScreenUiState()
    data class Error(val errorMessage: String) : HomeScreenUiState()
    data class Data(val notes: List<Note>) : HomeScreenUiState()

    companion object {
        fun initialData(notes: List<Note> = emptyList()) = Data(notes)
    }
}

sealed class HomeScreenUiEvent() : UIEvent {
    object OnAddNewClick : HomeScreenUiEvent()
    data class OnNoteClick(val id: Long) : HomeScreenUiEvent()
}

sealed class HomeScreenUiEffect() : UIEffect {
    data class NavigateToDetailScreen(val id: Long) : HomeScreenUiEffect()
    object NavigateToAddScreen : HomeScreenUiEffect()
}