package com.hbworld.likhit.view.detailView

import com.hbworld.likhit.base.UIEffect
import com.hbworld.likhit.base.UIEvent
import com.hbworld.likhit.base.UIState

sealed class DetailScreenUiState() : UIState {
    data class Error(val errorMessage: String) : DetailScreenUiState()
    object Loading : DetailScreenUiState()
    data class Success(val title: String, val description: String) : DetailScreenUiState()
}

sealed class DetailScreenUiEvent() : UIEvent {
    object OnBackClick : DetailScreenUiEvent()
    object OnEditClick : DetailScreenUiEvent()
    data class OnFirstComposition(val noteId: Long) : DetailScreenUiEvent()
}

sealed class DetailScreenUiEffect() : UIEffect {
    object NavigateBack : DetailScreenUiEffect()
}