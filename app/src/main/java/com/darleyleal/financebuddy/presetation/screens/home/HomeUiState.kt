package com.darleyleal.financebuddy.presetation.screens.home

import com.darleyleal.financebuddy.data.models.Registration

data class HomeUiState(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val value: String = "",
    val date: String = "",
    val type: String = "",
    val category: String = "",
    val error: String = "",
    val registrations: List<Registration> = emptyList(),
    val registration: Registration? = null
)
