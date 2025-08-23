package com.hbworld.likhit.ui.view.homeView

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hbworld.likhit.ui.component.AddButton
import com.hbworld.likhit.ui.component.SearchBar
import com.hbworld.likhit.data.local.NoteEntity
import com.hbworld.likhit.ui.theme.Pink40
import com.hbworld.likhit.ui.theme.Pink80

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToAddScreen: () -> Unit,
    navigateToDetailScreen: (Long) -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    when (state.value) {
        is HomeScreenUiState.Loading -> {}
        is HomeScreenUiState.Error -> {
            CreateNoteListUI(
                noteList = emptyList<NoteEntity>(),
                snackbarHostState = snackbarHostState,
                onAddClick = { viewModel.onEvent(HomeScreenUiEvent.OnAddNewClick) },
                onNoteClick = { viewModel.onEvent(HomeScreenUiEvent.OnNoteClick(it)) }
            )
        }

        is HomeScreenUiState.Data -> {
            CreateNoteListUI(
                noteList = (state.value as HomeScreenUiState.Data).noteData,
                snackbarHostState = snackbarHostState,
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
    noteList: List<NoteEntity>,
    snackbarHostState: SnackbarHostState,
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                count = noteList.size,
                key = { index -> noteList[index].id }) {
                NoteCard(
                    noteEntity = noteList[it],
                    onNoteClick = { id ->
                        onNoteClick(id)
                    }
                )
            }
        }
    }
}

@Composable
fun NoteCard(noteEntity: NoteEntity, onNoteClick: (Long) -> Unit) {
    Card(
        onClick = { onNoteClick(noteEntity.id!!) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(Pink40)
                .fillMaxSize()
        ) {
            Text(
                text = noteEntity.title,
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = noteEntity.description,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

//@Composable
//@Preview
//fun HomeScreenPreview() {
//    CreateNoteListUI(
//        listOf(
//            NoteEntity(1, "Title 1", "Description 1", 1L),
//            NoteEntity(
//                2,
//                "Title 2",
//                "Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2",
//                1L
//            ),
//            NoteEntity(3, "Title 3", "Description 3", 1L),
//        ), {}, {})
//}