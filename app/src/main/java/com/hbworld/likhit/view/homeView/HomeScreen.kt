package com.hbworld.likhit.view.homeView

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hbworld.likhit.component.AddButton
import com.hbworld.likhit.component.SearchBar
import com.hbworld.likhit.component.SnackBar

@Composable
fun HomeScreen(
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
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
    Scaffold(
        topBar = { SearchBar() },
        floatingActionButton = {
            AddButton(onAddClick = { })
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { pd ->


    }

}