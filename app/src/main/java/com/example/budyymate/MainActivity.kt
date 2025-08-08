package com.example.budyymate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.budyymate.presentation.navigation.Routes
import com.example.budyymate.presentation.screens.*
import com.example.budyymate.ui.theme.BudyyMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudyyMateTheme {
                BudgetMateApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetMateApp() {
    val navController = rememberNavController()
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val screens = listOf(
                    Screen(
                        route = Routes.DASHBOARD,
                        title = "Dashboard",
                        icon = Icons.Default.Home
                    ),
                    Screen(
                        route = Routes.TRANSACTIONS,
                        title = "Harcamalar",
                        icon = Icons.Default.List
                    ),
                    Screen(
                        route = Routes.ADD_TRANSACTION,
                        title = "Ekle",
                        icon = Icons.Default.Add
                    ),
                    Screen(
                        route = Routes.CATEGORIES,
                        title = "Kategoriler",
                        icon = Icons.Default.List
                    ),
                    Screen(
                        route = Routes.SETTINGS,
                        title = "Ayarlar",
                        icon = Icons.Default.Settings
                    )
                )

                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.DASHBOARD,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.DASHBOARD) { DashboardScreen(navController = navController) }
            composable(Routes.TRANSACTIONS) { TransactionsScreen() }
            composable(Routes.ADD_TRANSACTION) { AddTransactionScreen() }
            composable(Routes.CATEGORIES) { CategoriesScreen(navController = navController) }
            composable(Routes.SETTINGS) { SettingsScreen() }
        }
    }
}

data class Screen(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Preview(showBackground = true)
@Composable
fun BudgetMateAppPreview() {
    BudyyMateTheme {
        BudgetMateApp()
    }
}