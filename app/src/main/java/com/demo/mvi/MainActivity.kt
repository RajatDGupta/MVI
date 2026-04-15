package com.demo.mvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.auth.ui.navigation.authGraph
import com.demo.auth.ui.navigation.navigateToAuthGraph
import com.demo.core.navigation.Route
import com.demo.core.ui.theme.MVITheme
import com.demo.home.ui.navigation.homeGraph
import com.demo.home.ui.navigation.navigateToHomeGraph
import com.demo.mvi.ui.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    App(navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: androidx.navigation.NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash
    ) {
        composable<Route.Splash> {
            SplashScreen(onTimeout = {
                navController.navigateToAuthGraph()
            })
        }

        authGraph(
            onLoginSuccess = {
                navController.navigateToHomeGraph()
            },
            onRegisterClick = {
                // navController.navigate(Route.Register)
            }
        )

        homeGraph()
    }
}