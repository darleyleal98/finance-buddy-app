package com.darleyleal.financebuddy.presetation.screens.categories

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.presetation.navigation.NavigationProvider
import com.darleyleal.financebuddy.presetation.screens.categories.category_expenses.CategoryExpenses
import com.darleyleal.financebuddy.presetation.screens.categories.category_incomes.CategoryIncomes
import com.darleyleal.financebuddy.presetation.theme.FinanceBuddyTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryScreen(
    navigationProvider: NavigationProvider,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    wasClicked: Boolean = true
) {
    FinanceBuddyTheme {
        Column(modifier = modifier.padding(paddingValues)) {
            Text(
                text = stringResource(R.string.my_categories),
                fontSize = 22.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                color = when {
                    isSystemInDarkTheme() -> Color.Cyan
                    else -> Color.Black
                }
            )

            when (wasClicked) {
                true -> CategoryIncomes(navigationProvider = navigationProvider)
                else -> CategoryExpenses(navigationProvider = navigationProvider)
            }
        }
    }
}