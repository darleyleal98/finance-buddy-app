package com.darleyleal.financebuddy.presetation.screens.home.card_information

import com.darleyleal.financebuddy.data.models.Balance
import com.darleyleal.financebuddy.data.models.Registration

data class CardInformationUiState(
    var balance: Balance? = null,
    val value: String = "",
    val incomesList: List<Registration> = emptyList(),
    val expensesList: List<Registration> = emptyList()
)
