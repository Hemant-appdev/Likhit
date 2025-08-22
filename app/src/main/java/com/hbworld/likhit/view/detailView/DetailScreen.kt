package com.hbworld.likhit.view.detailView

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailScreen(viewModel: DetailScreenViewModel, noteId: Long) {

    Text(text = "Hello From Detail Screen with id -> $noteId")


}