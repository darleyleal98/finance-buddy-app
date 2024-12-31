package com.darleyleal.financebuddy.presenter.screens.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@Composable
fun ReportScreen(
    navigationProvider: NavigationProvider,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    FinanceBuddyTheme {
        Column(modifier.padding(paddingValues)) {

        }
    }
}