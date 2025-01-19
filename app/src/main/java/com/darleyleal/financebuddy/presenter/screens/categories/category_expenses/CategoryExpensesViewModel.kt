package com.darleyleal.financebuddy.presenter.screens.categories.category_expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.data.local.Category
import com.darleyleal.financebuddy.domain.usercases.CategoryUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryExpensesViewModel @Inject constructor(
    private val categoryUserCase: CategoryUserCase
) : ViewModel() {

    private val _listAllExpenses = MutableStateFlow<List<Category>>(emptyList())
    val listAllExpenses = _listAllExpenses.asStateFlow()

    private val _category = MutableStateFlow<Category?>(null)
    val category = _category.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    init {
        getAllExpenses()
    }

    fun updateNameField(newValue: String) {
        _name.value = newValue
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            categoryUserCase.getAllItemsByCategoryEqualsExpense().collect {
                _listAllExpenses.value = it
            }
        }
    }

    fun getExpenseById(id: Long?) {
        viewModelScope.launch {
            _category.value = listAllExpenses.value.find { it.id == id }
        }
    }

    fun update(id: Long, name: String, type: String) {
        viewModelScope.launch {
            categoryUserCase.update(id, name, type)
        }
    }

    fun delete(category: Category) {
        viewModelScope.launch {
            categoryUserCase.delete(category)
        }
    }

    fun validateField(text: String): Boolean {
        if (text != "") {
            return text.trim().isNotEmpty()
        }
        return false
    }
}