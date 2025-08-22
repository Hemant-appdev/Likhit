package com.hbworld.likhit.view.addView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hbworld.likhit.component.CenteredTransparentTextField
import com.hbworld.likhit.ui.theme.Pink80

@Composable
fun AddScreen(
    viewModel: AddViewModel,
    moreClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditAdd(
        onBackClick = { viewModel.onViewEvent(AddScreenUiEvent.OnBackClick) },
        saveClick = { viewModel.onViewEvent(AddScreenUiEvent.OnSaveClick) },
        moreClick = moreClick,
        title = state.title,
        description = state.description,
        onTitleChange = { viewModel.onViewEvent(AddScreenUiEvent.OnTitleChange(title = it)) },
        onDescriptionChange = {
            viewModel.onViewEvent(
                AddScreenUiEvent.OnDescriptionChange(
                    description = it
                )
            )
        }
    )
}

@Composable
private fun EditAdd(
    onBackClick: () -> Unit,
    saveClick: () -> Unit,
    moreClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    title: String,
    description: String
) {
    Scaffold(
        topBar = {
            EditTopBar(
                onBackClick = { onBackClick() },
                saveClick = { saveClick() },
                moreClick = { moreClick() }
            )
        },
        containerColor = Pink80,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            CenteredTransparentTextField(
                value = title,
                onValueChange = { onTitleChange(it) },
                placeholderText = "Enter Title",
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth()
            )

            CenteredTransparentTextField(
                value = description,
                onValueChange = { onDescriptionChange(it) },
                placeholderText = "Enter Description",
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
@Preview
private fun EditScreenPreview() {
    EditAdd(
        onBackClick = {},
        saveClick = {},
        moreClick = {},
        title = "",
        description = "",
        onTitleChange = {},
        onDescriptionChange = {}
    )
}