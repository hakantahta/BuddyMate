package com.example.budyymate.domain.repository

import com.example.budyymate.domain.model.Transaction
import com.example.budyymate.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    fun getCategories(): Flow<List<Category>>
    suspend fun addCategory(category: Category)
}