package com.hbworld.likhit.view.addView

import com.hbworld.likhit.base.UIEvent
import com.hbworld.likhit.base.UIState

sealed class AddScreenUiEvent() : UIEvent {
    object OnBackClick : AddScreenUiEvent()
    object OnSaveClick : AddScreenUiEvent()
    data class OnTitleChange(val title: String) : AddScreenUiEvent()
    data class OnDescriptionChange(val description: String) : AddScreenUiEvent()
}

sealed class AddScreenUiState() : UIState {
    object Success : AddScreenUiState()
    object Error : AddScreenUiState()
    object Loading : AddScreenUiState()
    data class State(val title: String, val description: String) : AddScreenUiState() {
        companion object {
            fun initialState() = State(title = "", description = "")
        }
    }
}