package com.hbworld.likhit.ui.view.detailView

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailScreen(viewModel: DetailScreenViewModel, noteId: Long) {

    Text(text = "Hello From Detail Screen with id -> $noteId")


}