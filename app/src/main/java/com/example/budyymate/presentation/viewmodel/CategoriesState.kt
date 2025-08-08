package com.example.budyymate.presentation.viewmodel

import com.example.budyymate.domain.model.Category

data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val newCategoryName: String = "",
    val newCategoryColor: String = "#FF0000",
    val isAddingCategory: Boolean = false,
    val error: String? = null
) 