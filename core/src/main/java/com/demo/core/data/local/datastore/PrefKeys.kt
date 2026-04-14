package com.demo.core.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PrefKeys {
    val USER_NAME = stringPreferencesKey("user_name")

    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    val USER_ID = intPreferencesKey("user_id")

    val LAST_SYNC_TIME = longPreferencesKey("last_sync_time")
}