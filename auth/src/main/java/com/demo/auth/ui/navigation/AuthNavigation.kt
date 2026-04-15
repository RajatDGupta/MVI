package com.demo.auth.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.demo.auth.ui.LoginScreen
import com.demo.core.navigation.Route

fun NavGraphBuilder.authGraph(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    navigation<Route.AuthGraph>(startDestination = Route.Login) {
        composable<Route.Login> {
            LoginScreen(
                onLoginSuccess = onLoginSuccess,
                onRegisterClick = onRegisterClick
            )
        }
        composable<Route.Register> {
            // RegisterScreen(onRegisterSuccess = { ... })
        }
    }
}

fun NavController.navigateToAuthGraph() {
    navigate(Route.AuthGraph) {
        popUpTo(0) { inclusive = true }
    }
}
