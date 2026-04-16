package com.demo.auth.ui.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.demo.auth.ui.LoginScreen
import com.demo.core.navigation.Route

fun EntryProviderScope<NavKey>.authEntries(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    entry<Route.Login> {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onRegisterClick = onRegisterClick
        )
    }
    entry<Route.Register> {
        // RegisterScreen()
    }
}
