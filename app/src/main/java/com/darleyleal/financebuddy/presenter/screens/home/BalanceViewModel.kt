package com.darleyleal.financebuddy.presenter.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.data.local.Balance
import com.darleyleal.financebuddy.domain.usercases.BalanceUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val balanceUserCase: BalanceUserCase
) : ViewModel() {
    data class UiState(
        var balance: Balance? = null,
        val value: String = ""
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            balanceUserCase.getBalance().collect { balance ->
                _uiState.update {
                    it.copy(
                        balance = balance
                    )
                }
            }
        }
    }

    fun updateBalance() {
        viewModelScope.launch {
            balanceUserCase.updateOrInsertBalance(uiState.value.value)
            cleanBalanceField()
        }
    }

    fun updateBalanceValueField(newValue: String) {
        _uiState.update { it.copy(value = newValue) }
    }

    fun validadeBalanceField(): Boolean {
        return with(uiState.value) { value.isNotEmpty() }
    }

    private fun cleanBalanceField() {
        _uiState.update { it.copy(value = "") }
    }
}