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
                
                _state.update {
                    it.copy(
                        isLoading = false,
                        transactions = transactions,
                        categories = categories,
                        totalBalance = totalBalance,
                        totalIncome = totalIncome,
                        totalExpense = totalExpense
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

    fun refresh() {
        loadDashboardData()
    }
} 