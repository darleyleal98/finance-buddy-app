package com.darleyleal.financebuddy.presetation.screens.home.card_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.domain.usercase.BalanceUseCase
import com.darleyleal.financebuddy.domain.usercase.RegistrationUserCase
import com.darleyleal.financebuddy.domain.utils.convertToCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardInformationViewModel @Inject constructor(
    private val balanceUseCase: BalanceUseCase,
    private val registration: RegistrationUserCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardInformationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllIncomes()
            getAllExpenses()
            getBalance()
        }
    }

    fun updateBalance() {
        viewModelScope.launch {
            balanceUseCase.updateOrInsertBalance(uiState.value.value)
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

    private fun getBalance() {
        viewModelScope.launch {
            balanceUseCase.getBalance().collect { balance ->
                _uiState.update {
                    it.copy(
                        balance = balance
                    )
                }
            }
        }
    }

    private fun getAllIncomes() {
        viewModelScope.launch {
            registration.getAllRegistrationEqualsIncomes().collect { registrations ->
                _uiState.update {
                    it.copy(
                        incomesList = registrations
                    )
                }
            }
        }
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            registration.getAllRegistrationEqualsExpense().collect { registrations ->
                _uiState.update {
                    it.copy(
                        expensesList = registrations
                    )
                }
            }
        }
    }

    fun calculateValueAllIncomes(): String {
        val result = _uiState.value.incomesList.sumOf {
            it.value.toString().toDoubleOrNull() ?: 0.0
        }
        return convertToCurrency(result.toFloat())
    }

    fun calculateValueAllExpenses(): String {
        val result = _uiState.value.expensesList.sumOf {
            it.value.toString().toDoubleOrNull() ?: 0.0
        }
        return convertToCurrency(result.toFloat())
    }

    fun convertAvailableBalanceToCurrency(): Float {
        val availableBalance =
            _uiState.value.balance?.availableBalance.toString().toDoubleOrNull() ?: 0.0

        val expenses = _uiState.value.expensesList.sumOf {
            it.value.toString().toDoubleOrNull() ?: 0.0
        }

        return availableBalance.toFloat() - expenses.toFloat()
    }
}