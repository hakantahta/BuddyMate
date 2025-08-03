package com.example.budyymate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budyymate.data.local.dao.TransactionDao
import com.example.budyymate.data.local.dao.CategoryDao
import com.example.budyymate.data.local.entity.TransactionEntity
import com.example.budyymate.data.local.entity.CategoryEntity

@Database(
    entities = [TransactionEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
}