package com.demo.core.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferenceDataStore {
    suspend fun <T> put(key: Preferences.Key<T>, value: T)
    fun <T> get(key: Preferences.Key<T>): Flow<T?>
    suspend fun <T> remove(key: Preferences.Key<T>)
}