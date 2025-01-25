package com.darleyleal.financebuddy.presetation.app

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.darleyleal.financebuddy.presetation.navigation.AppNavigation
import com.darleyleal.financebuddy.domain.utils.showBiometricLogin
import com.darleyleal.financebuddy.domain.utils.showSplahScreen
import com.darleyleal.financebuddy.domain.utils.viewModelProvider
import com.darleyleal.financebuddy.presetation.screens.analytics.AnalyticsViewModel
import com.darleyleal.financebuddy.presetation.screens.categories.CategoryViewModel
import com.darleyleal.financebuddy.presetation.screens.categories.category_expenses.CategoryExpensesViewModel
import com.darleyleal.financebuddy.presetation.screens.categories.category_incomes.CategoryIncomesViewModel
import com.darleyleal.financebuddy.presetation.screens.home.HomeViewModel
import com.darleyleal.financebuddy.presetation.screens.home.card_information.CardInformationViewModel
import com.darleyleal.financebuddy.presetation.screens.insert.InsertViewModel
import com.darleyleal.financebuddy.presetation.screens.start.StarViewModel
import com.darleyleal.financebuddy.presetation.theme.FinanceBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter",
        "RestrictedApi",
        "SourceLockedOrientationActivity"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val categoryExpensesViewModel: CategoryExpensesViewModel by viewModels()
        val categoryIncomesViewModel: CategoryIncomesViewModel by viewModels()
        val homeViewModel: HomeViewModel by viewModels()
        val analyticsViewModel: AnalyticsViewModel by viewModels()
        val startViewModel: StarViewModel by viewModels()
        val insertViewModel: InsertViewModel by viewModels()
        val newCategoryViewModel: CategoryViewModel by viewModels()
        val balanceViewModel: CardInformationViewModel by viewModels()
        
        val navigationProvider = viewModelProvider(
            categoryExpensesViewModel,
            categoryIncomesViewModel,
            homeViewModel,
            analyticsViewModel,
            startViewModel,
            insertViewModel,
            newCategoryViewModel,
            balanceViewModel
        )

        showSplahScreen(this)
        showBiometricLogin(startViewModel, this)

        setContent {
            requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

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