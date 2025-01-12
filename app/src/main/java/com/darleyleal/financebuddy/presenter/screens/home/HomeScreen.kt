package com.darleyleal.financebuddy.presenter.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.components.CardInformation
import com.darleyleal.financebuddy.presenter.components.HistoryInformations

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigationProvider: NavigationProvider,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val homeViewModel = navigationProvider.getViewModel(ViewModelKey.HOME) as HomeViewModel
    val registrationsList by homeViewModel.uiState.collectAsState()

    val balanceViewModel =
        navigationProvider.getViewModel(ViewModelKey.BALANCE) as CardInformationViewModel
    val uiState by balanceViewModel.uiState.collectAsState()

    var cardValuesIsVisible by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    Column(modifier.padding(paddingValues)) {
        CardInformation(
            valuesIsVisible = cardValuesIsVisible,
            balance = uiState.balance,
            onClickVisibilityButton = {
                cardValuesIsVisible = !cardValuesIsVisible
            },
            income = balanceViewModel.calculateValueAllIncomes(),
            expanse = balanceViewModel.calculateValueAllExpenses(),
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(32.dp),
            text = stringResource(R.string.my_history),
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
            registrationsList.registrations.forEach {
                HistoryInformations(registration = it)
            }
        }
    }
}