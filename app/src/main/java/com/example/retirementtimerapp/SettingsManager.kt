package com.example.retirementtimerapp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    private val SETTINGS_KEY = stringPreferencesKey("retirement_settings")

    val settingsFlow: Flow<RetirementSettings> = context.dataStore.data.map { preferences ->
        val json = preferences[SETTINGS_KEY]
        if (json != null) {
            try {
                Json.decodeFromString<RetirementSettings>(json)
            } catch (e: Exception) {
                RetirementSettings()
            }
        } else {
            RetirementSettings()
        }
    }

    suspend fun updateSettings(settings: RetirementSettings) {
        context.dataStore.edit { preferences ->
            preferences[SETTINGS_KEY] = Json.encodeToString(settings)
        }
    }
}
