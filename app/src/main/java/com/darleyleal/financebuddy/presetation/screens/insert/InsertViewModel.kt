package com.darleyleal.financebuddy.presetation.screens.insert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.domain.usercase.CategoryUserCase
import com.darleyleal.financebuddy.domain.usercase.RegistrationUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertViewModel @Inject constructor(
    private val registrationUserCase: RegistrationUserCase,
    private val categoryUserCase: CategoryUserCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsertUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllExpenses()
            getAllIncomes()
        }
    }

    fun updateName(newValue: String) {
        _uiState.update { it.copy(name = newValue) }
    }

    fun updateDescription(newValue: String) {
        _uiState.update { it.copy(description = newValue) }
    }

    fun updateValue(newValue: String) {
        _uiState.update { it.copy(value = newValue) }
    }

    fun updateDate(newValue: String) {
        _uiState.update { it.copy(date = newValue) }
    }

    fun updateSelectedType(newValue: String) {
        _uiState.update { it.copy(selectedType = newValue) }
    }

    fun selectCategory(category: String) {
        _uiState.update { it.copy(category = category) }
    }

    fun validateFormFields(): Boolean {
        val currentUiState = uiState.value
        return with(currentUiState) {
            name.isNotBlank() && description.isNotBlank() &&
                    value.isNotBlank() && date.isNotBlank()
                    && selectedType.isNotBlank()
        }
    }

    fun clearFields() {
        _uiState.update {
            it.copy(
                name = "",
                description = "",
                value = "",
                date = "",
                selectedType = "",
                category = "",
                error = null
            )
        }
    }

    fun insertRegistration() {
        viewModelScope.launch {
            when {
                validateFormFields() -> {
                    val currentUiState = uiState.value
                    registrationUserCase.insert(
                        name = currentUiState.name,
                        description = currentUiState.description,
                        value = currentUiState.value,
                        date = currentUiState.date,
                        type = currentUiState.selectedType,
                        category = currentUiState.category
                    )
                    clearFields()
                }
                else -> {
                    _uiState.update { it.copy(error = "This field is required!") }
                }
            }
        }
    }

    private fun getAllIncomes() {
        viewModelScope.launch {
            categoryUserCase.getAllItemsByCategoryEqualsIncome().collect { incomes ->
                _uiState.update {
                    it.copy(
                        incomes = incomes,
                        isLoading = true
                    )
                }
            }
        }
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            categoryUserCase.getAllItemsByCategoryEqualsExpense().collect { expenses ->
                _uiState.update {
                    it.copy(
                        expenses = expenses,
                        isLoading = true
                    )
                }
            }
        }
    }
}