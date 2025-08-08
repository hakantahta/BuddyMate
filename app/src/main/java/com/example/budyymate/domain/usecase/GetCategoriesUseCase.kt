package com.example.budyymate.domain.usecase

import com.example.budyymate.domain.model.Category
import com.example.budyymate.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.first

class GetCategoriesUseCase(private val repository: BudgetRepository) {
    suspend operator fun invoke(): List<Category> = repository.getCategories().first()
}