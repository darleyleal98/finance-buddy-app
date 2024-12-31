package com.darleyleal.financebuddy.presenter.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presenter.screens.home.components.CardInformation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigationProvider: NavigationProvider,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val homeViewModel = navigationProvider.getViewModel(ViewModelKey.HOME) as HomeViewModel
    var cardValuesIsVisible by remember { mutableStateOf(true) }

    Column(modifier.padding(paddingValues)) {
        CardInformation(valuesIsVisible = cardValuesIsVisible, onClickVisibilityButton = {
            cardValuesIsVisible = !cardValuesIsVisible
        })
    }
}