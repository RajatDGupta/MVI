package com.demo.mvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.demo.auth.ui.navigation.authEntries
import com.demo.core.data.local.datastore.PrefKeys
import com.demo.core.data.local.datastore.PreferenceDataStore
import com.demo.core.navigation.NavigationState
import com.demo.core.navigation.Navigator
import com.demo.core.navigation.Route
import com.demo.core.navigation.rememberNavigationState
import com.demo.core.navigation.toEntries
import com.demo.core.ui.theme.MVITheme
import com.demo.home.ui.navigation.homeEntries
import com.demo.mvi.ui.DetailsScreen
import com.demo.mvi.ui.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferenceDataStore: PreferenceDataStore

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
                    App(navigator, navigationState, Modifier.padding(innerPadding), preferenceDataStore)
                }
            }
        }
    }
}

@Composable
fun App(
    navigator: Navigator,
    navigationState: NavigationState,
    modifier: Modifier = Modifier,
    preferenceDataStore: PreferenceDataStore
) {
    val isLoggedIn by preferenceDataStore.get(PrefKeys.IS_LOGGED_IN).collectAsState(initial = false)

    val entryProvider = entryProvider {
        entry<Route.Splash> {
            SplashScreen(onTimeout = {
                navigator.removeRoute(Route.Splash::class)
                if (isLoggedIn == true) {
                    navigator.navigate(Route.Home)
                } else {
                    navigator.navigate(Route.Login)
                }
            })
        }

        authEntries({
            navigator.removeRoute(Route.Login::class)
            navigator.navigate(Route.Home)
        })

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
