package com.darleyleal.financebuddy.presenter.app.utils

import android.app.Activity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.screens.categories.category_expenses.CategoryExpensesViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.category_incomes.CategoryIncomesViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.custom_category_dialog.CategoryDialogViewModel
import com.darleyleal.financebuddy.presenter.screens.home.HomeViewModel
import com.darleyleal.financebuddy.presenter.screens.insert.InsertViewModel
import com.darleyleal.financebuddy.presenter.screens.reports.report_expenses.ReportExpensesViewModel
import com.darleyleal.financebuddy.presenter.screens.reports.reports_incomes.ReportsIncomesViewModel
import com.darleyleal.financebuddy.presenter.screens.start.StarViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal fun showSplahScreen(activity: Activity) {
    val splashScreen = activity.installSplashScreen()
    splashScreen.setKeepOnScreenCondition { true }

    CoroutineScope(Dispatchers.Main).launch {
        delay(1000L)
        splashScreen.setKeepOnScreenCondition { false }
    }
}

internal fun showBiometricLogin(activity: Activity) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(3000L)
    }
    //startViewModel.showBiometricPrompt(this)
}

internal fun viewModelProvider(
    categoryExpensesViewModel: CategoryExpensesViewModel,
    categoryIncomesViewModel: CategoryIncomesViewModel,
    homeViewModel: HomeViewModel,
    reportsIncomesViewModel: ReportsIncomesViewModel,
    reportsExpensesViewModel: ReportExpensesViewModel,
    startViewModel: StarViewModel,
    insertViewModel: InsertViewModel,
    newCategoryViewModel: CategoryDialogViewModel
): NavigationProvider {

    val navigationProvider = NavigationProvider(
        categoryExpensesViewModel,
        categoryIncomesViewModel,
        homeViewModel,
        reportsIncomesViewModel,
        reportsExpensesViewModel,
        startViewModel,
        insertViewModel,
        newCategoryViewModel
    )

    return navigationProvider
}