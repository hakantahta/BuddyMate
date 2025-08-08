package com.example.budyymate.domain.usecase

import com.example.budyymate.domain.model.Transaction
import com.example.budyymate.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.first

class GetAllTransactionsUseCase(private val repository: BudgetRepository) {
    suspend operator fun invoke(): List<Transaction> = repository.getAllTransactions().first()
}