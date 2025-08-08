package com.example.budyymate.presentation.viewmodel

data class SettingsState(
    val isLoading: Boolean = false,
    val isDarkMode: Boolean = false,
    val currency: String = "TRY",
    val language: String = "tr",
    val notificationsEnabled: Boolean = true,
    val error: String? = null
) 