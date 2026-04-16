package com.demo.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlin.reflect.KClass

/**
 * Handles navigation events (forward and back) by updating the navigation state.
 */
class Navigator(val state: NavigationState) {
    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            // This is a top level route, just switch to it.
            state.topLevelRoute = route
        } else {
            state.currentStack.add(route)
        }
    }

    /**
     * Standard back navigation.
     */
    fun goBack() {
        val currentStack = state.currentStack
        val currentRoute = currentStack.lastOrNull()

        // If we're at the base of the current route, go back to the start route stack.
        if (currentRoute == state.topLevelRoute) {
            if (state.topLevelRoute != state.startRoute) {
                state.topLevelRoute = state.startRoute
            }
        } else {
            currentStack.removeLastOrNull()
        }
    }

    /**
     * Pops the back stack until the specified [route] is reached.
     */
    fun popUpTo(route: NavKey, inclusive: Boolean = false) {
        val currentStack = state.currentStack
        val index = currentStack.indexOf(route)
        if (index != -1) {
            val targetSize = if (inclusive) index else index + 1
            while (currentStack.size > targetSize) {
                currentStack.removeAt(currentStack.size - 1)
            }
        }
    }

    /**
     * Removes all instances of a specific route type from the backstack.
     * Useful for removing "intermediate" screens like a verification or success screen.
     */
    fun removeRoute(routeClass: KClass<out NavKey>) {
        state.currentStack.removeAll { it::class == routeClass }
    }

    /**
     * Removes a specific route instance from the backstack.
     */
    fun removeRouteInstance(route: NavKey) {
        state.currentStack.remove(route)
    }

    /**
     * Clears the current backstack except for the top-level route.
     */
    fun clearStack() {
        val currentStack = state.currentStack
        while (currentStack.size > 1) {
            currentStack.removeAt(currentStack.size - 1)
        }
    }
}
