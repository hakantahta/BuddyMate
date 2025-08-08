package com.example.budyymate

import android.app.Application
import com.example.budyymate.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BudgetMateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BudgetMateApplication)
            modules(appModule)
        }
    }
}