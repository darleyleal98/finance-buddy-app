package com.darleyleal.financebuddy.presenter.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.usercases.CategoryUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUserCase: CategoryUserCase
) : ViewModel() {

    private val _category = MutableStateFlow("")
    val category = _category.asStateFlow()

    val radioOptionsList = listOf(Type.Income.name, Type.Expense.name)
    private val _radioOptionSelected = MutableStateFlow("")
    val radioOptionSelected = _radioOptionSelected.asStateFlow()

    fun updateCategoryField(newValue: String) {
        _category.value = newValue
    }

    fun updateRadioButton(newValue: String) {
        _radioOptionSelected.value = newValue
    }

    fun validateCategoryField(text: String): Boolean {
        if (text != "") {
            return text.trim().isNotEmpty()
        }
        return false
    }

    fun cleanField() {
        viewModelScope.launch {
            delay(1000)
            _category.value = ""
        }
    }

    fun insert(name: String, type: String) {
        viewModelScope.launch {
            categoryUserCase.insert(name = name, type = type)
            cleanField()
        }
    }
}