package com.example.budyymate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budyymate.domain.usecase.GetAllTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsState())
    val state: StateFlow<TransactionsState> = _state.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            try {
                val transactions = getAllTransactionsUseCase()
                _state.update {
                    it.copy(
                        isLoading = false,
                        transactions = transactions,
                        filteredTransactions = transactions
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "İşlemler yüklenirken hata oluştu"
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
        filterTransactions()
    }

    fun updateSelectedCategory(category: String?) {
        _state.update { it.copy(selectedCategory = category) }
        filterTransactions()
    }

    private fun filterTransactions() {
        val currentState = _state.value
        val query = currentState.searchQuery.lowercase()
        val selectedCategory = currentState.selectedCategory
        
        val filtered = currentState.transactions.filter { transaction ->
            val matchesQuery = query.isEmpty() || 
                transaction.title.lowercase().contains(query) ||
                transaction.description?.lowercase()?.contains(query) == true
            
            val matchesCategory = selectedCategory == null || 
                transaction.categoryName == selectedCategory
            
            matchesQuery && matchesCategory
        }
        
        _state.update { it.copy(filteredTransactions = filtered) }
    }

    fun refresh() {
        loadTransactions()
    }
} 