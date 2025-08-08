package com.example.budyymate.presentation.viewmodel

import com.example.budyymate.domain.model.Transaction
import com.example.budyymate.domain.model.Category

data class DashboardState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList(),
    val categories: List<Category> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val weeklyExpenses: Map<String, Double> = emptyMap(), // Kategori bazlı haftalık harcamalar
    val weeklyIncome: Double = 0.0,
    val weeklyExpense: Double = 0.0,
    val error: String? = null
) 