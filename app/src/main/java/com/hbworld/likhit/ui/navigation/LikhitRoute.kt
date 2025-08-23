package com.hbworld.likhit.ui.navigation

sealed class LikhitRoute(val route: String) {
    object HomeScreen : LikhitRoute("HOME_SCREEN")

    object AddScreen : LikhitRoute("ADD_SCREEN")

    object DetailScreen : LikhitRoute("DETAIL_SCREEN/{noteId}") {
        const val ARG_NOTE_ID = "noteId"
        fun route(noteId: Long) = "DETAIL_SCREEN/$noteId"
    }
}