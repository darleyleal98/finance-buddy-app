package com.darleyleal.financebuddy.presenter.screens.categories.category_expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@Composable
fun CategoryExpenses(
    navigationProvider: NavigationProvider,
    modifier: Modifier = Modifier
) {
    FinanceBuddyTheme {
        val categoryExpensesViewModel = navigationProvider.getViewModel(
            ViewModelKey.CATEGORY_EXPENSES
        ) as CategoryExpensesViewModel

        Column {
            Text(text = "Heloooooooooo")
        }
    }
}