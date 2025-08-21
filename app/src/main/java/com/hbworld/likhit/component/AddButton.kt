package com.hbworld.likhit.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hbworld.likhit.ui.theme.PurpleGrey40

@Composable
fun AddButton(onAddClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onAddClick() }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add new note",
            modifier = Modifier.size(32.dp),
            tint = PurpleGrey40
        )
    }
}

@Composable
@Preview
fun AddButtonPreview() {
    FloatingActionButton(
        onClick = { }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add new note",
            modifier = Modifier.size(32.dp),
            tint = PurpleGrey40
        )
    }
}