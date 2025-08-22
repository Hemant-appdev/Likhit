package com.hbworld.likhit.view.addView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hbworld.likhit.component.CenteredTransparentTextField
import com.hbworld.likhit.component.CircularLoader
import com.hbworld.likhit.ui.theme.Pink80
import kotlinx.coroutines.launch

@Composable
fun AddScreen(
    viewModel: AddViewModel,
    navigateBack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                AddScreenUiEffect.NavigateBack -> navigateBack()
                is AddScreenUiEffect.ShowToast -> {
                    scope.launch { snackBarHostState.showSnackbar(message = it.message) }

                }

                is AddScreenUiEffect.ShowToastAndNavigateBack -> {
                    snackBarHostState.showSnackbar(message = it.message)
                    navigateBack()
                }
            }
        }
    }

    AddScreenContent(
        state = state,
        snackBarHostState = snackBarHostState,
        onEvent = viewModel::onViewEvent
    )


}

@Composable
fun AddScreenContent(
    state: AddScreenUiState.State,
    snackBarHostState: SnackbarHostState,
    onEvent: (AddScreenUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            EditTopBar(
                onBackClick = { onEvent(AddScreenUiEvent.OnBackClick) },
                saveClick = { onEvent(AddScreenUiEvent.OnSaveClick) },
                moreClick = { }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        containerColor = Pink80,
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                CenteredTransparentTextField(
                    value = state.title,
                    onValueChange = { onEvent(AddScreenUiEvent.OnTitleChange(title = it)) },
                    placeholderText = "Enter Title",
                    fontSize = 32.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                CenteredTransparentTextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(
                            AddScreenUiEvent.OnDescriptionChange(
                                description = it
                            )
                        )
                    },
                    placeholderText = "Enter Description",
                    fontSize = 32.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            ShowLoader(state.isLoading)
        }
    }
}

@Composable
fun ShowLoader(isLoading: Boolean) {
    if (isLoading) {
        CircularLoader()
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    val sampleState = AddScreenUiState.State(
        title = "Preview Title",
        description = "This is a description for the preview.",
        isLoading = true // You can toggle this to preview the loader
    )
    AddScreenContent(
        state = sampleState,
        snackBarHostState = remember { SnackbarHostState() },
        onEvent = {}
    )
}