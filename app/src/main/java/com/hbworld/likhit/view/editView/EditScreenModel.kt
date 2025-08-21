package com.hbworld.likhit.view.editView

import com.hbworld.likhit.base.UIEvent
import com.hbworld.likhit.base.UIState

sealed class EditScreenUIEvent() : UIEvent {
    object OnBackClick : EditScreenUIEvent()
    object OnSaveClick : EditScreenUIEvent()
    data class OnTitleChange(val title: String) : EditScreenUIEvent()
    data class OnDescriptionChange(val description: String) : EditScreenUIEvent()
}

sealed class EditScreenUIState() : UIState {
    object Success : EditScreenUIState()
    object Error : EditScreenUIState()
    object Loading : EditScreenUIState()
    data class State(val title: String, val description: String) : EditScreenUIState() {
        companion object {
            fun initialState() = State("", "")
        }
    }
}