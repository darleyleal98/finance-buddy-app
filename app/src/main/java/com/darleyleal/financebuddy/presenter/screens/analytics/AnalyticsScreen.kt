package com.darleyleal.financebuddy.presenter.screens.analytics

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.components.AnalyticsChart
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@Composable
fun AnalyticsScreen(
    navigationProvider: NavigationProvider,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val viewModel = navigationProvider.getViewModel(
        ViewModelKey.ANALYTICS
    ) as AnalyticsViewModel

    val incomes by viewModel.incomes.collectAsState()
    val expenses by viewModel.expenses.collectAsState()
    val months by viewModel.months.collectAsState()

    FinanceBuddyTheme {
        Column(modifier.padding(paddingValues)) {
            AnalyticsChart(
                monthsList = months,
                incomesList = incomes,
                expensesList = expenses
            )
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.per_month),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                color = when {
                    isSystemInDarkTheme() -> {
                        Color.Cyan
                    }

                    else -> {
                        Color.Black
                    }
                }
            )
            // HistoryInformations(registration = ) -> TODO()
        }
    }
}