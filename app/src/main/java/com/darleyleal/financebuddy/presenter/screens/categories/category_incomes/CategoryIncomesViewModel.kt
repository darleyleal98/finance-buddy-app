package com.darleyleal.financebuddy.presenter.screens.categories.category_incomes

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
class CategoryIncomesViewModel @Inject constructor(
    private val categoryUsercase: CategoryUserCase
) : ViewModel() {

    private val _listAllIncomes = MutableStateFlow<List<Category>>(emptyList())
    val listAllIncomes = _listAllIncomes.asStateFlow()

    private val _category = MutableStateFlow<Category?>(null)
    val category = _category.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    init {
        viewModelScope.launch {
            categoryUsercase.getAllItemsByCategoryEqualsIncome().collect {
                _listAllIncomes.value = it
            }
        }
    }

    fun updateNameField(newValue: String) {
        _name.value = newValue
    }

    private fun getAllIncomes() {
        viewModelScope.launch {
            categoryUsercase.getAllItemsByCategoryEqualsIncome().collect {
                _listAllIncomes.value = it
            }
        }
    }

    fun getIncomeById(id: Long?) {
        viewModelScope.launch {
            _category.value = listAllIncomes.value.find { it.id == id }
        }
    }

    fun update(name: String) {
        viewModelScope.launch {
            category.value?.id?.let {
                categoryUsercase.update(it, name)
            }
            getAllIncomes()
        }
    }

    fun validateField(text: String): Boolean {
        if (text != "") {
            return text.trim().isNotEmpty()
        }
        return false
    }
}