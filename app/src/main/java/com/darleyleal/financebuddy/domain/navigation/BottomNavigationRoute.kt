package com.darleyleal.financebuddy.domain.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.darleyleal.financebuddy.domain.enums.Routes

data class BottomNavigationRoute(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottonNavigationItems = listOf(
    BottomNavigationRoute(
        route = Routes.HomeScreen.name,
        label = "Home",
        icon = Icons.Default.Home
    ),
    BottomNavigationRoute(
        route = Routes.ReportsScreen.name,
        label = "Report",
        icon = Icons.Default.BarChart
    ),
    BottomNavigationRoute(
        route = Routes.CategoriesScreen.name,
        label = "Category",
        icon = Icons.Default.List
    )
)