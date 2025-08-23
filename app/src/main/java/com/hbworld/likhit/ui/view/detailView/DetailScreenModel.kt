package com.hbworld.likhit.ui.view.detailView

import com.hbworld.likhit.ui.view.common.UIEffect
import com.hbworld.likhit.ui.view.common.UIEvent
import com.hbworld.likhit.ui.view.common.UIState

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