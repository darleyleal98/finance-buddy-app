package com.darleyleal.financebuddy.domain.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.darleyleal.financebuddy.domain.enums.Routes
import com.darleyleal.financebuddy.presenter.screens.categories.CategoriesScreen
import com.darleyleal.financebuddy.presenter.screens.home.HomeScreen
import com.darleyleal.financebuddy.presenter.screens.insert.InsertScreen
import com.darleyleal.financebuddy.presenter.screens.main.MainScreen
import com.darleyleal.financebuddy.presenter.screens.reports.ReportScreen
import com.darleyleal.financebuddy.presenter.screens.start.StartScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    navigationProvider: NavigationProvider
) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    var selectedIndexRegistrationScreen by remember { mutableIntStateOf(0) }

    NavHost(startDestination = Routes.StartScreen.name, navController = navController) {
        composable(route = Routes.StartScreen.name) {
            StartScreen(navigationProvider,
                onNavigateToHomeScreen = {
                    navController.navigate(Routes.MainScreen.name)
                }
            )
        }
        composable(route = Routes.MainScreen.name) {
            MainScreen(
                navigationProvider = navigationProvider,
                selectedItemIndex = selectedItemIndex,
                selectedItemIndexUpdate = {
                    selectedItemIndex = it
                },
                onNavigateToInsertScreenWithIndice = {
                    selectedIndexRegistrationScreen = it

                    navController.navigate(Routes.InsertScreen.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(route = Routes.HomeScreen.name) {
            HomeScreen(
                paddingValues = paddingValues,
                navigationProvider = navigationProvider
            )
        }
        composable(route = Routes.InsertScreen.name) {
            InsertScreen(
                paddingValues = paddingValues,
                navigationProvider = navigationProvider,
                onPopBackStack = {
                    navController.popBackStack()
                },
                indice = selectedIndexRegistrationScreen
            )
        }
        composable(route = Routes.ReportsScreen.name) {
            ReportScreen(
                paddingValues = paddingValues,
                navigationProvider = navigationProvider
            )
        }
        composable(route = Routes.CategoriesScreen.name) {
            CategoriesScreen(
                navigationProvider = navigationProvider,
                paddingValues = paddingValues
            )
        }
    }
}