package com.hbworld.likhit.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    onNoteClick: (Int) -> Unit
) {

    Column {
        Button(onClick = { onNoteClick(1) }) {

            Text(text = "Press Mee")

        }

        Text(text = "Hello From Home Screen")

    }
}

@Composable
@Preview
fun HomeScreenPreview(){
    Column {
        Button(onClick = {  }) {

            Text(text = "Press Mee")

        }

        Text(text = "Hello From Home Screen")

    }
}