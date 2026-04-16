package com.demo.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Splash : Route

    @Serializable
    data object Login : Route
    
    @Serializable
    data object Register : Route

    @Serializable
    data object Home : Route

    @Serializable
    data class Details(val id: String) : Route
}
