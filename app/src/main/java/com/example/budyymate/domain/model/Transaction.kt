package com.example.budyymate.domain.model

data class Transaction(
    val id: Int = 0,
    val categoryId: Int,
    val amount: Double,
    val description: String,
    val date: Long
)