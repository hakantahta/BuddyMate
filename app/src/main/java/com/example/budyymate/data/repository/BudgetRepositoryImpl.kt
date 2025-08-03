package com.example.budyymate.data.repository

import com.example.budyymate.data.local.dao.TransactionDao
import com.example.budyymate.data.local.dao.CategoryDao
import com.example.budyymate.data.local.entity.toDomain
import com.example.budyymate.data.local.entity.toEntity
import com.example.budyymate.domain.model.Transaction
import com.example.budyymate.domain.model.Category
import com.example.budyymate.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : BudgetRepository {
    override fun getAllTransactions(): Flow<List<Transaction>> =
        transactionDao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun addTransaction(transaction: Transaction) =
        transactionDao.insert(transaction.toEntity())

    override fun getCategories(): Flow<List<Category>> =
        categoryDao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun addCategory(category: Category) =
        categoryDao.insert(category.toEntity())
}