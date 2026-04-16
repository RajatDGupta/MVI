# Navigation 3 Reference

### Routes
```kotlin
@Serializable
sealed interface Route : NavKey {
    @Serializable data object Home : Route
    @Serializable data class Details(val id: String) : Route
}
```

### Basic Navigation
```kotlin
navigator.navigate(Route.Home)
navigator.goBack()
```

### Passing Data
```kotlin
// Navigate
navigator.navigate(Route.Details(id = "123"))

// Receive
entry<Route.Details> { key ->
    DetailsScreen(id = key.id)
}
```

### Backstack Control (Pop/Remove)
```kotlin
// Pop back to specific key
navigator.popUpTo(Route.Home, inclusive = false)

// Remove specific screen from history
navigator.removeRoute(Route.Register::class)
navigator.removeRouteInstance(routeInstance)

// Reset current stack
navigator.clearStack()
```

### Implementation
```kotlin
// Entry Provider
val entryProvider = entryProvider {
    authEntries(navigator)
    homeEntries()
}

// UI Display
NavDisplay(
    entries = navigationState.toEntries(entryProvider),
    onBack = { navigator.goBack() }
)
```
