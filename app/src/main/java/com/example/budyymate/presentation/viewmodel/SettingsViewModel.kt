package com.example.budyymate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budyymate.data.local.datastore.UserPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        loadUserPreferences()
    }

    private fun loadUserPreferences() {
        viewModelScope.launch {
            userPreferencesManager.userPreferencesFlow.collect { preferences ->
                _state.update {
                    it.copy(
                        isDarkMode = preferences.isDarkMode,
                        currency = preferences.currency,
                        language = preferences.language,
                        notificationsEnabled = preferences.notificationsEnabled
                    )
                }
            }
        }
    }

    fun toggleDarkMode() {
        val newValue = !_state.value.isDarkMode
        _state.update { it.copy(isDarkMode = newValue) }
        
        viewModelScope.launch {
            userPreferencesManager.updateDarkMode(newValue)
        }
    }

    fun updateCurrency(currency: String) {
        _state.update { it.copy(currency = currency) }
        
        viewModelScope.launch {
            userPreferencesManager.updateCurrency(currency)
        }
    }

    fun updateLanguage(language: String) {
        _state.update { it.copy(language = language) }
        
        viewModelScope.launch {
            userPreferencesManager.updateLanguage(language)
        }
    }

    fun toggleNotifications() {
        val newValue = !_state.value.notificationsEnabled
        _state.update { it.copy(notificationsEnabled = newValue) }
        
        viewModelScope.launch {
            userPreferencesManager.updateNotificationsEnabled(newValue)
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
} 