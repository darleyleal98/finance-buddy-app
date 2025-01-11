package com.darleyleal.financebuddy.presenter.app.utils

import android.app.Activity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.app.MainActivity
import com.darleyleal.financebuddy.presenter.screens.categories.category_expenses.CategoryExpensesViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.category_incomes.CategoryIncomesViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.custom_category_dialog.CategoryDialogViewModel
import com.darleyleal.financebuddy.presenter.screens.home.CardInformationViewModel
import com.darleyleal.financebuddy.presenter.screens.home.HomeViewModel
import com.darleyleal.financebuddy.presenter.screens.insert.InsertViewModel
import com.darleyleal.financebuddy.presenter.screens.analytics.AnalyticsViewModel
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

internal fun showBiometricLogin(startVideModel: StarViewModel, activity: MainActivity) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(3000L)
    }
    startVideModel.showBiometricPrompt(activity)
}

internal fun viewModelProvider(
    categoryExpensesViewModel: CategoryExpensesViewModel,
    categoryIncomesViewModel: CategoryIncomesViewModel,
    homeViewModel: HomeViewModel,
    analytics: AnalyticsViewModel,
    startViewModel: StarViewModel,
    insertViewModel: InsertViewModel,
    newCategoryViewModel: CategoryDialogViewModel,
    balanceViewModel: CardInformationViewModel
): NavigationProvider {

    val navigationProvider = NavigationProvider(
        categoryExpensesViewModel,
        categoryIncomesViewModel,
        homeViewModel,
        analytics,
        startViewModel,
        insertViewModel,
        newCategoryViewModel,
        balanceViewModel
    )

    return navigationProvider
}