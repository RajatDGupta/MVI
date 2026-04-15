package com.demo.home.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.demo.core.navigation.Route
import com.demo.home.ui.HomeScreen

fun NavGraphBuilder.homeGraph() {
    navigation<Route.HomeGraph>(startDestination = Route.Home) {
        composable<Route.Home> {
            HomeScreen()
        }
    }
}

fun NavController.navigateToHomeGraph() {
    navigate(Route.HomeGraph) {
        popUpTo(0) { inclusive = true }
    }
}
