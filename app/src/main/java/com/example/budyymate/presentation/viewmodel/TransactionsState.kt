package com.example.budyymate.presentation.viewmodel

import com.example.budyymate.domain.model.Transaction

data class TransactionsState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val filteredTransactions: List<Transaction> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val error: String? = null
) 