package com.darleyleal.financebuddy.presenter.screens.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.financebuddy.data.local.Registration
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.usercases.RegistrationUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
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
        prepareCharData(monthlyTotals)

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
    ): Map<YearMonth, List<Registration>> {

        val allRegistrations = incomes + expenses

        return allRegistrations.groupBy { registration ->
            val dateParts = registration.date.split("/")
            val day = dateParts[0].toInt()
            val month = dateParts[1].toInt()
            val year = dateParts[2].toInt()
            YearMonth.of(year, month)
        }
    }

    private fun calculateMonthlyTotals(
        groupedRegistration: Map<YearMonth, List<Registration>>
    ): Map<String, Pair<Double, Double>> {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())

        return groupedRegistration.mapKeys { entry ->
            val yearMonth = entry.key
            val formattedMonthYear = yearMonth.format(formatter)
            formattedMonthYear
        }.mapValues { (_, registrationsOfMonth) ->
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

    /**
     * Prepara os dados para o gráfico, para que os meses sejam exibidos na ordem correta.
     *
     * @param monthlyTotals É um mapa onde as chaves são strings no formato "Mês Ano" (ex: "Janeiro 2025")
     *                      e os valores são pares de Double (expense, income).
     *
     * @param sortedYearMonths  Criar uma lista de YearMonth para que será ordenada.
     *
     * @param expenses Extrai os valores de despesas.
     * @param incomes Extrai os valores dos rendimentos.
     * @param months Formata os meses para exibição no gráfico.
     *
     * @return Triple<List<Double>, List<Double>, List<String>>
     *         Onde:
     *         - first: Lista de rendimentos (Double).
     *         - second: Lista de despesas (Double).
     *         - third: Lista de meses formatados para exibição (String).
     */
    private fun prepareCharData(
        monthlyTotals: Map<String, Pair<Double, Double>>
    ): Triple<List<Double>, List<Double>, List<String>> {
        val sortedYearMonths = monthlyTotals.keys.map {
            YearMonth.parse(it, DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()))
        }.sorted()

        val expenses = sortedYearMonths.map { yearMonth ->
            monthlyTotals[yearMonth.format(
                DateTimeFormatter.ofPattern(
                    "MMMM yyyy",
                    Locale.getDefault()
                )
            )]?.first ?: 0.0
        }.toList()

        val incomes = sortedYearMonths.map { yearMonth ->
            monthlyTotals[yearMonth.format(
                DateTimeFormatter.ofPattern(
                    "MMMM yyyy",
                    Locale.getDefault()
                )
            )]?.second ?: 0.0
        }.toList()

        val months = sortedYearMonths.map { yearMonth ->
            yearMonth.format(DateTimeFormatter.ofPattern("MMMM", Locale.getDefault()))
        }.toList()

        return Triple(incomes, expenses, months)
    }
}