package com.darleyleal.financebuddy.presenter.screens.analytics

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
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
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.domain.usercases.utils.convertDate
import com.darleyleal.financebuddy.domain.usercases.utils.convertToCurrency
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

    val allRegistrations by viewModel.registrations.collectAsState()
    val registrationsByMonth = allRegistrations.groupBy { convertDate(it.date) }
    val scrollState = rememberScrollState()

    FinanceBuddyTheme {
        Column(
            modifier.padding(paddingValues)
        ) {
            AnalyticsChart(
                monthsList = months,
                incomesList = incomes,
                expensesList = expenses
            )
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "History by date",
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

            Column(
                modifier = modifier
                    .size(width = 600.dp, height = 532.dp)
                    .verticalScroll(scrollState)
            ) {
                registrationsByMonth.forEach { (date, items) ->
                    Text(
                        modifier = modifier.padding(16.dp),
                        text = date,
                        fontSize = 18.sp,
                        color = when {
                            isSystemInDarkTheme() -> Color.Cyan
                            else -> Color.Black
                        }
                    )

                    items.forEach {
                        Row(
                            modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Icon(
                                    imageVector = when (it.category) {
                                        Type.Income.name -> Icons.Filled.ArrowDropUp
                                        else -> Icons.Filled.ArrowDropDown
                                    },
                                    contentDescription = null,
                                    tint = when (it.category) {
                                        Type.Income.name -> Color.Green
                                        else -> Color.Red
                                    }
                                )
                                Text(
                                    text = it.name.lowercase().replaceFirstChar(Char::titlecase),
                                    fontSize = 16.sp
                                )
                            }
                            Text(text = convertToCurrency(it.value))
                        }
                    }
                    Spacer(modifier =modifier.padding(bottom = 8.dp))
                }
            }
        }
    }
}