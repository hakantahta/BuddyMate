package com.example.budyymate.di

import android.content.Context
import androidx.room.Room
import com.example.budyymate.data.local.BudgetDatabase
import com.example.budyymate.data.local.dao.TransactionDao
import com.example.budyymate.data.local.dao.CategoryDao
import com.example.budyymate.data.repository.BudgetRepositoryImpl
import com.example.budyymate.domain.repository.BudgetRepository
import com.example.budyymate.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BudgetDatabase =
        Room.databaseBuilder(
            context,
            BudgetDatabase::class.java,
            "budget_db"
        ).build()

    @Provides
    fun provideTransactionDao(db: BudgetDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideCategoryDao(db: BudgetDatabase): CategoryDao = db.categoryDao()

    @Provides
    @Singleton
    fun provideBudgetRepository(
        transactionDao: TransactionDao,
        categoryDao: CategoryDao
    ): BudgetRepository = BudgetRepositoryImpl(transactionDao, categoryDao)

    @Provides
    fun provideGetAllTransactionsUseCase(repository: BudgetRepository) = GetAllTransactionsUseCase(repository)

    @Provides
    fun provideAddTransactionUseCase(repository: BudgetRepository) = AddTransactionUseCase(repository)

    @Provides
    fun provideGetCategoriesUseCase(repository: BudgetRepository) = GetCategoriesUseCase(repository)

    @Provides
    fun provideAddCategoryUseCase(repository: BudgetRepository) = AddCategoryUseCase(repository)
}