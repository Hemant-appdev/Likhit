package com.hbworld.likhit.view.homeView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hbworld.likhit.component.AddButton
import com.hbworld.likhit.component.SearchBar
import com.hbworld.likhit.data.local.Note
import com.hbworld.likhit.ui.theme.Pink40
import com.hbworld.likhit.ui.theme.Pink80

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToAddScreen: () -> Unit,
    navigateToDetailScreen: (Long) -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    when (state.value) {
        is HomeScreenUiState.Loading -> {}
        is HomeScreenUiState.Error -> {}
        is HomeScreenUiState.Data -> {
            CreateNoteListUI(
                state = state.value as HomeScreenUiState.Data,
                onAddClick = { viewModel.onEvent(HomeScreenUiEvent.OnAddNewClick) },
                onNoteClick = { viewModel.onEvent(HomeScreenUiEvent.OnNoteClick(it)) }
            )
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeScreenUiEffect.NavigateToAddScreen -> {
                    navigateToAddScreen()
                }

                is HomeScreenUiEffect.NavigateToDetailScreen -> {
                    navigateToDetailScreen(effect.id)
                }
            }
        }
    }
}

@Composable
fun CreateNoteListUI(
    state: HomeScreenUiState.Data,
    onAddClick: () -> Unit,
    onNoteClick: (Long) -> Unit
) {
    Scaffold(
        containerColor = Pink80,
        topBar = { SearchBar() },
        floatingActionButton = {
            AddButton(onAddClick = {
                onAddClick()
            })
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(18.dp)
        ) {
            items(
                count = state.notes.size,
                key = { index -> state.notes[index].id ?: index }) {
                NoteCard(
                    note = state.notes[it],
                    onNoteClick = { id ->
                        onNoteClick(id)
                    }
                )
            }
        }
    }
}

@Composable
fun NoteCard(note: Note, onNoteClick: (Long) -> Unit) {
    Card(
        onClick = { onNoteClick(note.id!!) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(Pink40)
                .fillMaxSize()
        ) {
            Text(
                text = note.title,
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = note.description,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    CreateNoteListUI(
        HomeScreenUiState.initialData(
            listOf(
                Note(1, "Title 1", "Description 1", 1L),
                Note(
                    2,
                    "Title 2",
                    "Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2",
                    1L
                ),
                Note(3, "Title 3", "Description 3", 1L),
            )
        ), {}, {})
}