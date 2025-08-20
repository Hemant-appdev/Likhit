package com.hbworld.likhit.view

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hbworld.likhit.component.AddButton
import com.hbworld.likhit.component.SearchBar
import com.hbworld.likhit.component.SnackBar
import com.hbworld.likhit.navigation.LikhitRoute
import com.hbworld.likhit.ui.theme.LikhitTheme

@Composable
@Preview
fun LikhitView() {
    val navController = rememberNavController()
    LikhitTheme {
        Scaffold(
            topBar = { SearchBar() },
            snackbarHost = { SnackBar() },
            floatingActionButton = { AddButton() },
            floatingActionButtonPosition = FabPosition.End,
            content = { pd ->
                NavHost(
                    navController = navController,
                    startDestination = LikhitRoute.HOME_SCREEN
                ) {
                    homeScreenRoute(navController = navController)
                    detailScreenRoute(navController = navController)

                }
            }
        )
    }
}

private fun NavGraphBuilder.homeScreenRoute(navController: NavHostController) {
    composable(LikhitRoute.HOME_SCREEN) {
        HomeScreen(onNoteClick = {
            navController.navigate(LikhitRoute.DETAIL_SCREEN)
        })
    }
}

private fun NavGraphBuilder.detailScreenRoute(navController: NavHostController) {
    composable(LikhitRoute.DETAIL_SCREEN) {
        DetailScreen()
    }
}

