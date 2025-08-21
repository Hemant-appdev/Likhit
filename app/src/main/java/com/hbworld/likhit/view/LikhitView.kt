package com.hbworld.likhit.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hbworld.likhit.component.SnackBar
import com.hbworld.likhit.navigation.LikhitRoute
import com.hbworld.likhit.ui.theme.LikhitTheme
import com.hbworld.likhit.view.editView.EditViewModel
import com.hbworld.likhit.view.editView.EditScreen
import com.hbworld.likhit.view.detailView.DetailScreen
import com.hbworld.likhit.view.homeView.HomeScreen

@Composable
@Preview
fun LikhitView() {
    val navController = rememberNavController()
    LikhitTheme {
        Scaffold(
            snackbarHost = { SnackBar() },
            content = { pd ->
                NavHost(
                    modifier = Modifier.padding(pd),
                    navController = navController,
                    startDestination = LikhitRoute.HOME_SCREEN
                ) {
                    homeScreenRoute(navController = navController)
                    detailScreenRoute(navController = navController)
                    addScreenRoute(navController = navController)
                }
            }
        )
    }
}

private fun NavGraphBuilder.homeScreenRoute(navController: NavHostController) {
    composable(LikhitRoute.HOME_SCREEN) {
        HomeScreen(
            onNoteClick = { navController.navigate(LikhitRoute.DETAIL_SCREEN) },
            onAddClick = { navController.navigate(LikhitRoute.ADD_SCREEN) }
        )
    }
}

private fun NavGraphBuilder.detailScreenRoute(navController: NavHostController) {
    composable(LikhitRoute.DETAIL_SCREEN) {
        DetailScreen()
    }
}

private fun NavGraphBuilder.addScreenRoute(navController: NavHostController) {
    composable(LikhitRoute.ADD_SCREEN) {
        val viewModel = hiltViewModel<EditViewModel>()
        EditScreen(viewModel = viewModel) {}
    }
}

