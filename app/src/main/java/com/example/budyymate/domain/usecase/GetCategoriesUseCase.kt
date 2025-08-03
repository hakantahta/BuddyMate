package com.example.budyymate.domain.usecase

import com.example.budyymate.domain.model.Category
import com.example.budyymate.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(private val repository: BudgetRepository) {
    operator fun invoke(): Flow<List<Category>> = repository.getCategories()
}