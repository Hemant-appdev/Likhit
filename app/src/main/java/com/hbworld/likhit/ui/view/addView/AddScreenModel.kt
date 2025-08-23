package com.hbworld.likhit.ui.view.addView

import com.hbworld.likhit.ui.view.common.UIEffect
import com.hbworld.likhit.ui.view.common.UIEvent
import com.hbworld.likhit.ui.view.common.UIState

sealed class AddScreenUiEvent() : UIEvent {
    object OnBackClick : AddScreenUiEvent()
    object OnSaveClick : AddScreenUiEvent()
    data class OnTitleChange(val title: String) : AddScreenUiEvent()
    data class OnDescriptionChange(val description: String) : AddScreenUiEvent()
}

sealed class AddScreenUiState() : UIState {
    data class State(val title: String, val description: String, val isLoading: Boolean) : AddScreenUiState() {
        companion object {
            fun initialState() = State(title = "", description = "", isLoading = false)
        }
    }
}

sealed class AddScreenUiEffect() : UIEffect {
    object NavigateBack : AddScreenUiEffect()
    data class ShowToast(val message: String) : AddScreenUiEffect()
    data class ShowToastAndNavigateBack(val message: String) : AddScreenUiEffect()
}