package com.example.budyymate.domain.usecase

import com.example.budyymate.domain.model.Transaction
import com.example.budyymate.domain.repository.BudgetRepository

class AddTransactionUseCase(private val repository: BudgetRepository) {
    suspend operator fun invoke(transaction: Transaction) = repository.addTransaction(transaction)
}