package com.catalincozma.ui.navigation

sealed class NavGraph(val route: String, val label: String) {
    data object MovieDetails : NavGraph("details/{movieId}", "MovieDetails") {
        fun createRoute(movieId: Int) = "details/$movieId"
    }
}