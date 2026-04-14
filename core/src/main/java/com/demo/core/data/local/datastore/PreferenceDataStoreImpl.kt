package com.demo.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataStore {

    override suspend fun <T> put(key: Preferences.Key<T>, value: T) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    override fun <T> get(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.map { prefs ->
            prefs[key]
        }
    }

    override suspend fun <T> remove(key: Preferences.Key<T>) {
        dataStore.edit { prefs ->
            prefs.remove(key)
        }
    }
}