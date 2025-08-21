package com.hbworld.likhit.view.homeView

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hbworld.likhit.component.AddButton
import com.hbworld.likhit.component.SearchBar
import com.hbworld.likhit.component.SnackBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    when (state.value) {
        is HomeScreenUiState.Loading -> {}
        is HomeScreenUiState.Error -> {}
        is HomeScreenUiState.Data -> {
            CreateNoteListUI(state.value as HomeScreenUiState.Data, onAddClick)
        }
    }
}

@Composable
fun CreateNoteListUI(state: HomeScreenUiState.Data, onAddClick: () -> Unit) {

    Scaffold(
        topBar = { SearchBar() },
        floatingActionButton = {
            AddButton(onAddClick = {
                onAddClick()
            })
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { pd ->


    }

}

@Composable
@Preview
fun HomeScreenPreview() {
    CreateNoteListUI(HomeScreenUiState.initialData(), {})
}