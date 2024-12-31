package com.darleyleal.financebuddy.presenter.screens.reports.report_expenses

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@Composable
fun ReportExpenses(
    navigationProvider: NavigationProvider,
    modifier: Modifier = Modifier
) {
    FinanceBuddyTheme {

        val reportsExpensesViewModel = navigationProvider.getViewModel(
            ViewModelKey.REPORTS_EXPENSES
        ) as ReportExpensesViewModel

    }
}