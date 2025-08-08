package com.example.budyymate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budyymate.domain.usecase.GetAllTransactionsUseCase
import com.example.budyymate.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class DashboardViewModel(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            try {
                val transactions = getAllTransactionsUseCase()
                val categories = getCategoriesUseCase()
                
                val totalIncome = transactions.filter { it.amount > 0 }.sumOf { it.amount }
                val totalExpense = transactions.filter { it.amount < 0 }.sumOf { -it.amount }
                val totalBalance = totalIncome - totalExpense
                
                // Son işlemleri al (en son 5 işlem)
                val recentTransactions = transactions
                    .sortedByDescending { it.date }
                    .take(5)
                
                // Haftalık verileri hesapla
                val weeklyData = calculateWeeklyData(transactions)
                
                _state.update {
                    it.copy(
                        isLoading = false,
                        transactions = transactions,
                        recentTransactions = recentTransactions,
                        categories = categories,
                        totalBalance = totalBalance,
                        totalIncome = totalIncome,
                        totalExpense = totalExpense,
                        weeklyExpenses = weeklyData.expenses,
                        weeklyIncome = weeklyData.income,
                        weeklyExpense = weeklyData.expense
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Bilinmeyen hata oluştu"
                    )
                }
            }
        }
    }

    private fun calculateWeeklyData(transactions: List<com.example.budyymate.domain.model.Transaction>): WeeklyData {
        val calendar = Calendar.getInstance()
        val currentWeekStart = calendar.apply {
            set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        
        val currentWeekEnd = currentWeekStart + (7 * 24 * 60 * 60 * 1000) - 1
        
        val weeklyTransactions = transactions.filter { 
            it.date in currentWeekStart..currentWeekEnd 
        }
        
        val weeklyIncome = weeklyTransactions.filter { it.amount > 0 }.sumOf { it.amount }
        val weeklyExpense = weeklyTransactions.filter { it.amount < 0 }.sumOf { -it.amount }
        
        // Kategori bazlı harcamaları hesapla
        val weeklyExpenses = weeklyTransactions
            .filter { it.amount < 0 } // Sadece harcamalar
            .groupBy { it.categoryName }
            .mapValues { (_, transactions) -> 
                transactions.sumOf { -it.amount } 
            }
        
        return WeeklyData(
            income = weeklyIncome,
            expense = weeklyExpense,
            expenses = weeklyExpenses
        )
    }

    fun refresh() {
        loadDashboardData()
    }

    private data class WeeklyData(
        val income: Double,
        val expense: Double,
        val expenses: Map<String, Double>
    )
} 