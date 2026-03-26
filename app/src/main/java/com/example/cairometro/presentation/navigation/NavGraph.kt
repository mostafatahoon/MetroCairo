package com.example.cairometroapp.presentation.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cairometroapp.domain.repository.MetroRepository
import com.example.cairometroapp.presentation.ui.home.HomeScreen
import com.example.cairometroapp.presentation.ui.home.HomeViewModel
import com.example.cairometroapp.presentation.ui.route.RouteResultScreen
import com.example.cairometroapp.presentation.ui.route.RouteResultViewModel
import com.example.cairometroapp.presentation.ui.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object RouteResult : Screen("route_result/{start}/{end}") {
        fun createRoute(start: String, end: String) = "route_result/$start/$end"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    repository: MetroRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn(animationSpec = tween(700)) },
        exitTransition = { fadeOut(animationSpec = tween(700)) }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToHome = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(repository) as T
                }
            })
            
            val uiState by viewModel.uiState.collectAsState()
            
            HomeScreen(
                uiState = uiState,
                onStartStationChanged = viewModel::onStartStationChanged,
                onEndStationChanged = viewModel::onEndStationChanged,
                onSwapClick = viewModel::swapStations,
                onSearchClick = {
                    if (uiState.startStation.isNotEmpty() && uiState.endStation.isNotEmpty()) {
                        navController.navigate(Screen.RouteResult.createRoute(uiState.startStation, uiState.endStation))
                    }
                }
            )
        }

        composable(
            route = Screen.RouteResult.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
        ) { backStackEntry ->
            val start = backStackEntry.arguments?.getString("start") ?: ""
            val end = backStackEntry.arguments?.getString("end") ?: ""
            
            val viewModel: RouteResultViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RouteResultViewModel(repository, start, end) as T
                }
            })

            val uiState by viewModel.uiState.collectAsState()
            
            RouteResultScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
