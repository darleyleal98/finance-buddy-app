package com.darleyleal.financebuddy.presetation.screens.insert

import com.darleyleal.financebuddy.data.models.Category
import com.darleyleal.financebuddy.data.models.Registration

data class InsertUiState(
    val name: String = "",
    val description: String = "",
    val value: String = "",
    val date: String = "",
    val selectedType: String = "",
    val category: String = "",
    val incomes: List<Category> = emptyList(),
    val expenses: List<Category> = emptyList(),
    val registrations: List<Registration> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)