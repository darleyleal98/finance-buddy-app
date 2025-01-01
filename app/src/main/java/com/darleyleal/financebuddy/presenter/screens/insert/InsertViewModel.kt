package com.darleyleal.financebuddy.presenter.screens.insert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.data.local.Category
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.usercases.CategoryUserCase
import com.darleyleal.financebuddy.domain.usercases.RegistrationUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertViewModel @Inject constructor(
    private val registrationUserCase: RegistrationUserCase,
    private val categoryUserCase: CategoryUserCase
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _value = MutableStateFlow("")
    val value = _value.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    val radioOptionsList = MutableStateFlow<List<String>>(emptyList())
    private val _radioOptionSelected = MutableStateFlow("")
    val radioOptionSelected = _radioOptionSelected.asStateFlow()

    fun updateNameTextField(newValue: String) {
        _name.value = newValue
    }

    fun updateDescriptionTextField(newValue: String) {
        _description.value = newValue
    }

    fun updateValueTextField(newValue: String) {
        _value.value = newValue
    }

    fun updateDateTextField(newValue: String) {
        _date.value = newValue
    }

    fun updateRadioButtonTextField(newValue: String) {
        _radioOptionSelected.value = newValue
    }

    fun validateFormFields(): Boolean {
        when {
            _name.value.trim().isEmpty() || _description.value.trim()
                .isEmpty() || _value.value.trim().isEmpty() || _date.value.trim().isEmpty()
            -> return false
        }
        return true
    }

    fun validateField(text: String): Boolean {
        if (text != "") {
            return text.trim().isNotEmpty()
        }
        return false
    }

    fun cleanFields() {
        viewModelScope.launch {
            delay(1000)
            _name.value = ""
            _description.value = ""
            _value.value = ""
            _date.value = ""
        }
    }

    fun insert(
        name: String, description: String, value: String,
        date: String, type: String
    ) {
        if (validateFormFields()) {
            viewModelScope.launch {
                registrationUserCase.insert(
                    name = name,
                    description = description,
                    value = value,
                    date = date,
                    type = type
                )
            }
            cleanFields()
        }
    }
}