package com.demo.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Splash : Route
    
    @Serializable
    data object AuthGraph : Route
    
    @Serializable
    data object Login : Route
    
    @Serializable
    data object Register : Route

    @Serializable
    data object HomeGraph : Route

    @Serializable
    data object Home : Route
}
