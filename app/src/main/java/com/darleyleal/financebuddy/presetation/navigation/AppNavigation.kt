package com.darleyleal.financebuddy.presetation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.darleyleal.financebuddy.domain.enums.Routes
import com.darleyleal.financebuddy.presetation.screens.analytics.AnalyticsScreen
import com.darleyleal.financebuddy.presetation.screens.categories.CategoryScreen
import com.darleyleal.financebuddy.presetation.screens.home.HomeScreen
import com.darleyleal.financebuddy.presetation.screens.insert.InsertScreen
import com.darleyleal.financebuddy.presetation.screens.main.MainScreen
import com.darleyleal.financebuddy.presetation.screens.start.StartScreen

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
                    navController.navigate(Routes.MainScreen.name) {
                        popUpTo(Routes.StartScreen.name) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
                    navController.navigate(Routes.InsertScreen.name)
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
        composable(route = Routes.AnalyticsScreen.name) {
            AnalyticsScreen(
                paddingValues = paddingValues,
                navigationProvider = navigationProvider
            )
        }
        composable(route = Routes.CategoriesScreen.name) {
            CategoryScreen(
                navigationProvider = navigationProvider,
                paddingValues = paddingValues
            )
        }
    }
}