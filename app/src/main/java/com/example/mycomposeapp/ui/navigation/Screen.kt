package com.example.mycomposeapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Boookmarked : Screen("favorite")
    object Profil : Screen("about")
    object Detail : Screen("home/{seriesId}") {
        fun createRoute(seriesId: Int) = "home/$seriesId"
    }
}