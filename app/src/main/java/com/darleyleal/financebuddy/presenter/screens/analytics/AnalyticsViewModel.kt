package com.darleyleal.financebuddy.presenter.screens.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.data.local.Registration
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.usercases.RegistrationUserCase
import com.darleyleal.financebuddy.domain.usercases.utils.formatMonthAndYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val registrationUserCase: RegistrationUserCase
) : ViewModel() {

    init {
        getAllExpenses()
        getAllIncomes()
        getAllRegistrations()
    }

    private val _incomesListRegistrations = MutableStateFlow<List<Registration>>(
        emptyList()
    )

    private val _expensesListRegistrations = MutableStateFlow<List<Registration>>(
        emptyList()
    )

    private val _registrations = MutableStateFlow<List<Registration>>(emptyList())
    val registrations = _registrations.asStateFlow()

    val charData = combine(
        _incomesListRegistrations, _expensesListRegistrations
    ) { incomes, expenses ->

        val groupedRegistrations = groupRegistrationsByMonth(incomes, expenses)
        val monthlyTotals = calculateMonthlyTotals(groupedRegistrations)
        prepareCharDate(monthlyTotals)

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        Triple(emptyList(), emptyList(), emptyList())
    )

    val incomes = charData.map { it.first }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    val expenses = charData.map { it.second }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    val months = charData.map { it.third }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    private fun getAllIncomes() {
        viewModelScope.launch {
            registrationUserCase.getAllRegistrationEqualsIncomes().collect {
                _incomesListRegistrations.value = it
            }
        }
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            registrationUserCase.getAllRegistrationEqualsExpense().collect {
                _expensesListRegistrations.value = it
            }
        }
    }

    private fun getAllRegistrations() {
        viewModelScope.launch {
            registrationUserCase.getAllRegistrations().collect {
                _registrations.value = it
            }
        }
    }

    private fun groupRegistrationsByMonth(
        incomes: List<Registration>, expenses: List<Registration>
    ): Map<String, List<Registration>> {

        val allRegistrations = incomes + expenses

        return allRegistrations.groupBy { registration ->
            val (day, month, year) = registration.date.split("/")
           formatMonthAndYear(month, year)
        }
    }

    private fun calculateMonthlyTotals(
        groupedRegistration: Map<String, List<Registration>>
    ): Map<String, Pair<Double, Double>> {

        return groupedRegistration.mapValues { (_, registrationsOfMonth) ->
            val incomes = registrationsOfMonth.filter {
                it.category == Type.Income.name
            }.sumOf {
                it.value.toDouble()
            }

            val expenses = registrationsOfMonth.filter {
                it.category == Type.Expense.name
            }.sumOf {
                it.value.toDouble()
            }

            expenses to incomes
        }
    }

    private fun prepareCharDate(
        monthlyTotals: Map<String, Pair<Double, Double>>
    ): Triple<List<Double>, List<Double>, List<String>> {

        val months = monthlyTotals.keys.toList()
        val incomes = monthlyTotals.values.map { it.first }.toList()
        val expenses = monthlyTotals.values.map { it.second }.toList()

        return Triple(incomes, expenses, months)
    }
}