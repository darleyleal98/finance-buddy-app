package com.darleyleal.financebuddy.presetation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.data.models.Registration
import com.darleyleal.financebuddy.domain.usercase.CategoryUserCase
import com.darleyleal.financebuddy.domain.usercase.RegistrationUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val registrationUserCase: RegistrationUserCase,
    private val categoryUserCase: CategoryUserCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllRegistrations()
    }

    fun updateId(newValue: Long) {
        _uiState.update { it.copy(id = newValue) }
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

    fun updateCategory(newValue: String) {
        _uiState.update { it.copy(category = newValue) }
    }

    fun updateType(newValue: String) {
        _uiState.update { it.copy(type = newValue) }
    }

    fun clearFields() {
        _uiState.update {
            it.copy(
                name = "",
                description = "",
                value = "",
                date = "",
                category = "",
                type = "",
                error = ""
            )
        }
    }

    fun validateFormFields(): Boolean {
        val currentUiState = uiState.value
        return with(currentUiState) {
            name.isNotBlank() && description.isNotBlank() &&
                    value.isNotBlank() && date.isNotBlank()
        }
    }

    private fun getAllRegistrations() {
        viewModelScope.launch {
            registrationUserCase.getAllRegistrations().collect { registrations ->
                _uiState.update {
                    it.copy(
                        registrations = registrations
                    )
                }
            }
        }
    }

    fun delete(registration: Registration) {
        viewModelScope.launch {
            registrationUserCase.delete(registration)
            getAllRegistrations()
        }
    }

    fun getRegistrationById(id: Long) {
        viewModelScope.launch {
            registrationUserCase.getRegistrationById(id).collect { registration ->
                _uiState.update {
                    it.copy(
                        registration = registration
                    )
                }
            }
        }
    }

    fun updateRegistration(
        id: Long, name: String, description: String, value: String,
        date: String, category: String, type: String
    ) {
        viewModelScope.launch {
            registrationUserCase.update(
                id = id,
                name = name,
                description = description,
                value = value,
                date = date,
                category = category,
                type = type
            )
        }
    }
}