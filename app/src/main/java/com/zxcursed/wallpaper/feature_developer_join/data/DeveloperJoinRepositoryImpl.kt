package com.zxcursed.wallpaper.feature_developer_join.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.zxcursed.wallpaper.feature_developer_join.domain.DeveloperJoinRepository
import kotlinx.coroutines.flow.first

class DeveloperJoinRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DeveloperJoinRepository {

    companion object {
        private val DEVELOPER_KEY = booleanPreferencesKey("developer_status")
    }

    override suspend fun putBoolean(value: Boolean) {
        dataStore.edit { pref ->
            pref[DEVELOPER_KEY] = value
        }
    }

    override suspend fun getBoolean(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[DEVELOPER_KEY] ?: false
    }

}