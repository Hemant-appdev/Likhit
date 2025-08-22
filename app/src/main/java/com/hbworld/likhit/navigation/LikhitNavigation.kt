package com.hbworld.likhit.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hbworld.likhit.view.addView.AddScreen
import com.hbworld.likhit.view.addView.AddViewModel
import com.hbworld.likhit.view.detailView.DetailScreen
import com.hbworld.likhit.view.detailView.DetailScreenViewModel
import com.hbworld.likhit.view.homeView.HomeScreen
import com.hbworld.likhit.view.homeView.HomeViewModel

@Composable
fun LikhitNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LikhitRoute.HomeScreen.route) {
        homeScreenRoute(navController = navController)
        detailScreenRoute(navController = navController)
        addScreenRoute(navController = navController)
    }
}

private fun NavGraphBuilder.homeScreenRoute(navController: NavHostController) {
    composable(LikhitRoute.HomeScreen.route) {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(
            viewModel = viewModel,
            navigateToAddScreen = { navController.navigate(LikhitRoute.AddScreen.route) },
            navigateToDetailScreen = { navController.navigate(LikhitRoute.DetailScreen.route(noteId = it)) }
        )
    }
}

private fun NavGraphBuilder.detailScreenRoute(navController: NavHostController) {
    composable(
        route = LikhitRoute.DetailScreen.route,
        arguments = listOf(navArgument(name = LikhitRoute.DetailScreen.ARG_NOTE_ID) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        val viewModel = hiltViewModel<DetailScreenViewModel>()
        DetailScreen(
            viewModel = viewModel,
            noteId = backStackEntry.arguments?.getLong(LikhitRoute.DetailScreen.ARG_NOTE_ID)
                ?.toLong() ?: 0L
        )
    }
}


private fun NavGraphBuilder.addScreenRoute(navController: NavHostController) {
    composable(LikhitRoute.AddScreen.route) {
        val viewModel = hiltViewModel<AddViewModel>()
        AddScreen(
            viewModel = viewModel,
            navigateBack = { navController.navigate(LikhitRoute.HomeScreen.route) }
        )
    }
}