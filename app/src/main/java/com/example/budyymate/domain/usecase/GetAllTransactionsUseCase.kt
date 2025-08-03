package com.example.budyymate.domain.usecase

import com.example.budyymate.domain.model.Transaction
import com.example.budyymate.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow

class GetAllTransactionsUseCase(private val repository: BudgetRepository) {
    operator fun invoke(): Flow<List<Transaction>> = repository.getAllTransactions()
}