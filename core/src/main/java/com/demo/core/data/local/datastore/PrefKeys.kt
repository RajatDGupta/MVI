package com.demo.core.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey

object PrefKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
}