package com.example.budyymate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budyymate.presentation.screens.*
import androidx.compose.ui.Modifier

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.DASHBOARD
        , modifier = modifier
    ) {
        composable(Routes.DASHBOARD) { DashboardScreen() }
        composable(Routes.TRANSACTIONS) { TransactionsScreen() }
        composable(Routes.ADD_TRANSACTION) { AddTransactionScreen() }
        composable(Routes.CATEGORIES) { CategoriesScreen() }
        composable(Routes.SETTINGS) { SettingsScreen() }
    }
}