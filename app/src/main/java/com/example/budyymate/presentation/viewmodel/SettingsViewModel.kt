package com.example.budyymate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    fun toggleDarkMode() {
        _state.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun updateCurrency(currency: String) {
        _state.update { it.copy(currency = currency) }
    }

    fun updateLanguage(language: String) {
        _state.update { it.copy(language = language) }
    }

    fun toggleNotifications() {
        _state.update { it.copy(notificationsEnabled = !it.notificationsEnabled) }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
} 