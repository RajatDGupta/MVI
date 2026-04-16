package com.demo.mvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.demo.auth.ui.navigation.authEntries
import com.demo.core.navigation.NavigationState
import com.demo.core.navigation.Navigator
import com.demo.core.navigation.Route
import com.demo.core.navigation.rememberNavigationState
import com.demo.core.navigation.toEntries
import com.demo.core.ui.theme.MVITheme
import com.demo.home.ui.navigation.homeEntries
import com.demo.mvi.ui.DetailsScreen
import com.demo.mvi.ui.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVITheme {
                val navigationState = rememberNavigationState(
                    startRoute = Route.Splash,
                    topLevelRoutes = setOf(Route.Splash, Route.Login, Route.Home)
                )
                val navigator = remember { Navigator(navigationState) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(navigator, navigationState, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun App(
    navigator: Navigator,
    navigationState: NavigationState,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        entry<Route.Splash> {
            SplashScreen(onTimeout = {
                navigator.removeRoute(Route.Splash::class)
                navigator.navigate(Route.Login)
            })
        }

        authEntries(
            onLoginSuccess = {
                navigator.navigate(Route.Home)
            },
            onRegisterClick = {
                navigator.navigate(Route.Details(id = "user_123"))
            }
        )

        homeEntries()
        
        entry<Route.Details> { key ->
            DetailsScreen(id = key.id)
        }
    }

    NavDisplay(
        modifier = modifier,
        entries = navigationState.toEntries(entryProvider),
        onBack = { 
            navigator.goBack()
        }
    )
}
