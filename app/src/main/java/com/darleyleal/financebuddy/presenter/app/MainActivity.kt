package com.darleyleal.financebuddy.presenter.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.darleyleal.financebuddy.domain.navigation.AppNavigation
import com.darleyleal.financebuddy.presenter.app.utils.showBiometricLogin
import com.darleyleal.financebuddy.presenter.app.utils.showSplahScreen
import com.darleyleal.financebuddy.presenter.app.utils.viewModelProvider
import com.darleyleal.financebuddy.presenter.screens.categories.category_expenses.CategoryExpensesViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.category_incomes.CategoryIncomesViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.custom_category_dialog.CategoryDialogViewModel
import com.darleyleal.financebuddy.presenter.screens.home.BalanceViewModel
import com.darleyleal.financebuddy.presenter.screens.home.HomeViewModel
import com.darleyleal.financebuddy.presenter.screens.insert.InsertViewModel
import com.darleyleal.financebuddy.presenter.screens.reports.report_expenses.ReportExpensesViewModel
import com.darleyleal.financebuddy.presenter.screens.reports.reports_incomes.ReportsIncomesViewModel
import com.darleyleal.financebuddy.presenter.screens.start.StarViewModel
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val categoryExpensesViewModel: CategoryExpensesViewModel by viewModels()
        val categoryIncomesViewModel: CategoryIncomesViewModel by viewModels()
        val homeViewModel: HomeViewModel by viewModels()
        val reportsIncomesViewModel: ReportsIncomesViewModel by viewModels()
        val reportsExpensesViewModel: ReportExpensesViewModel by viewModels()
        val startViewModel: StarViewModel by viewModels()
        val insertViewModel: InsertViewModel by viewModels()
        val newCategoryViewModel: CategoryDialogViewModel by viewModels()
        val balanceViewModel: BalanceViewModel by viewModels()

        val navigationProvider = viewModelProvider(
            categoryExpensesViewModel,
            categoryIncomesViewModel,
            homeViewModel,
            reportsIncomesViewModel,
            reportsExpensesViewModel,
            startViewModel,
            insertViewModel,
            newCategoryViewModel,
            balanceViewModel
        )

        showSplahScreen(this)
        showBiometricLogin(startViewModel, this)

        setContent {
            val navController = rememberNavController()
            FinanceBuddyTheme {
                Scaffold {
                    AppNavigation(
                        navController = navController,
                        paddingValues = it,
                        navigationProvider = navigationProvider
                    )
                }
            }
        }
    }
}