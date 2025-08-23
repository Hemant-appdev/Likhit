package com.hbworld.likhit.ui.view.homeView

import com.hbworld.likhit.ui.view.common.UIEffect
import com.hbworld.likhit.ui.view.common.UIEvent
import com.hbworld.likhit.ui.view.common.UIState
import com.hbworld.likhit.data.local.NoteEntity

sealed class HomeScreenUiState() : UIState {
    object Loading : HomeScreenUiState()
    data class Error(val errorMessage: String) : HomeScreenUiState()
    data class Data(val noteData: List<NoteEntity>) : HomeScreenUiState()

    companion object {
        fun initialData(noteData: List<NoteEntity> = emptyList()) = Data(noteData)
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