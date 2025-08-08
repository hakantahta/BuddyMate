package com.example.budyymate.domain.model

data class Transaction(
    val id: Int = 0,
    val title: String,
    val categoryId: Int,
    val categoryName: String,
    val amount: Double,
    val description: String?,
    val date: Long
)