package com.darleyleal.financebuddy.presetation.screens.categories.category_incomes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.data.models.Category
import com.darleyleal.financebuddy.domain.usercase.CategoryUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryIncomesViewModel @Inject constructor(
    private val categoryUserCase: CategoryUserCase
) : ViewModel() {

    private val _listAllIncomes = MutableStateFlow<List<Category>>(emptyList())
    val listAllIncomes = _listAllIncomes.asStateFlow()

    private val _category = MutableStateFlow<Category?>(null)
    val category = _category.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    init { getAllIncomes() }

    fun updateNameField(newValue: String) {
        _name.value = newValue
    }

    private fun getAllIncomes() {
        viewModelScope.launch {
            categoryUserCase.getAllItemsByCategoryEqualsIncome().collect {
                _listAllIncomes.value = it
            }
        }
    }

    fun getIncomeById(id: Long?) {
        viewModelScope.launch {
            _category.value = listAllIncomes.value.find { it.id == id }
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