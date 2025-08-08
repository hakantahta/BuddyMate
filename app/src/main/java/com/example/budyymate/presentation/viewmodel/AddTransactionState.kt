package com.example.budyymate.presentation.viewmodel

import com.example.budyymate.domain.model.Category

data class AddTransactionState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val title: String = "",
    val amount: String = "",
    val description: String = "",
    val selectedCategoryId: Int? = null,
    val isExpense: Boolean = true,
    val isSaving: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
) 