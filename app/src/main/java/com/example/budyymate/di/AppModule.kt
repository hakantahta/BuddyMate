package com.example.budyymate.di

import android.content.Context
import androidx.room.Room
import com.example.budyymate.data.local.BudgetDatabase
import com.example.budyymate.data.local.dao.CategoryDao
import com.example.budyymate.data.local.dao.TransactionDao
import com.example.budyymate.data.local.datastore.UserPreferencesManager
import com.example.budyymate.data.repository.BudgetRepositoryImpl
import com.example.budyymate.domain.repository.BudgetRepository
import com.example.budyymate.domain.usecase.*
import com.example.budyymate.notification.AlarmScheduler
import com.example.budyymate.presentation.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            BudgetDatabase::class.java,
            "budget_db"
        ).build()
    }

    single { get<BudgetDatabase>().transactionDao() }

    single { get<BudgetDatabase>().categoryDao() }

    single<BudgetRepository> {
        BudgetRepositoryImpl(
            transactionDao = get(),
            categoryDao = get()
        )
    }

    factory { GetAllTransactionsUseCase(get()) }

    factory { AddTransactionUseCase(get()) }

    factory { GetCategoriesUseCase(get()) }

    factory { AddCategoryUseCase(get()) }

    // UserPreferencesManager
    single { UserPreferencesManager(androidContext()) }

    // AlarmScheduler
    single { AlarmScheduler(androidContext()) }

    // ViewModels
    factory { DashboardViewModel(get(), get()) }

    factory { TransactionsViewModel(get()) }

    factory { CategoriesViewModel(get(), get()) }

    factory { AddTransactionViewModel(get(), get()) }

    factory { SettingsViewModel(get(), get()) }
}