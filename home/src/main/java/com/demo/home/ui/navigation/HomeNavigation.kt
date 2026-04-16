package com.demo.home.ui.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.demo.core.navigation.Route
import com.demo.home.ui.HomeScreen

fun EntryProviderScope<NavKey>.homeEntries() {
    entry<Route.Home> {
        HomeScreen()
    }
}
