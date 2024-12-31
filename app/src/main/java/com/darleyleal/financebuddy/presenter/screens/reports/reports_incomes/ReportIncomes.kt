package com.darleyleal.financebuddy.presenter.screens.reports.reports_incomes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@Composable
fun ReportIncomes(
    navigationProvider: NavigationProvider,
    modifier: Modifier = Modifier
) {
    FinanceBuddyTheme {

        val reportIncomesViewModel = navigationProvider.getViewModel(
            ViewModelKey.REPORT_INCOMES
        ) as ReportsIncomesViewModel

    }
}