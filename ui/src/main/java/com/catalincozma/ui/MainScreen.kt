package com.catalincozma.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.catalincozma.ui.navigation.NavGraph
import com.catalincozma.ui.screen.BottomNavItem
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.catalincozma.ui.screen.BottomNavigationBar
import com.catalincozma.ui.screen.movie.MoviesScreen
import com.catalincozma.ui.screen.animation.scaleFadeExitTransitions
import com.catalincozma.ui.screen.animation.scaleFadeTransitions
import com.catalincozma.ui.screen.details.MovieDetailsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedItem = BottomNavItem.entries.find {
        it.route == (navBackStackEntry?.destination?.route ?: BottomNavItem.Home.route)
    }

    Scaffold(
        bottomBar = {
            selectedItem?.let {
                BottomNavigationBar(
                    selectedItem = it,
                    onItemSelected = { item ->
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(
                BottomNavItem.Home.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popExitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None }
            ) {
                MoviesScreen(
                    paddingValues = padding,
                    onMovieClick = { movieId ->
                        navController.navigate(NavGraph.MovieDetails.createRoute(movieId))
                    }
                )
            }

            composable(
                BottomNavItem.Favorites.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {

            }

            composable(
                BottomNavItem.Search.route, enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {

            }

            composable(
                route = NavGraph.MovieDetails.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType }),
                enterTransition = { scaleFadeTransitions() },
                exitTransition = { scaleFadeExitTransitions() },
                popEnterTransition = { scaleFadeTransitions() },
                popExitTransition = { scaleFadeExitTransitions() }
            ) {
                MovieDetailsScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}
