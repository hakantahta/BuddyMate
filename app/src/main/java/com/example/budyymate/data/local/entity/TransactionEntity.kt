package com.example.budyymate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val categoryId: Int,
    val categoryName: String,
    val amount: Double,
    val description: String?,
    val date: Long
)