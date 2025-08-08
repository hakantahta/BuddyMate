package com.example.budyymate.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

data class UserPreferences(
    val isDarkMode: Boolean = false,
    val currency: String = "TRY",
    val language: String = "tr",
    val notificationsEnabled: Boolean = true
)

class UserPreferencesManager(private val context: Context) {

    companion object {
        private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        private val CURRENCY = stringPreferencesKey("currency")
        private val LANGUAGE = stringPreferencesKey("language")
        private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data.map { preferences ->
        UserPreferences(
            isDarkMode = preferences[IS_DARK_MODE] ?: false,
            currency = preferences[CURRENCY] ?: "TRY",
            language = preferences[LANGUAGE] ?: "tr",
            notificationsEnabled = preferences[NOTIFICATIONS_ENABLED] ?: true
        )
    }

    suspend fun updateDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDarkMode
        }
    }

    suspend fun updateCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[CURRENCY] = currency
        }
    }

    suspend fun updateLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }

    suspend fun updateNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = enabled
        }
    }
} 