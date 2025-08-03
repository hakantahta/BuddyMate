package com.example.budyymate.domain.usecase

import com.example.budyymate.domain.model.Category
import com.example.budyymate.domain.repository.BudgetRepository

class AddCategoryUseCase(private val repository: BudgetRepository) {
    suspend operator fun invoke(category: Category) = repository.addCategory(category)
}