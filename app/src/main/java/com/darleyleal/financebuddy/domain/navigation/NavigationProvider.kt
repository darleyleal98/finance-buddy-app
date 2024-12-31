package com.darleyleal.financebuddy.domain.navigation

import androidx.lifecycle.ViewModel
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presenter.screens.categories.category_expenses.CategoryExpensesViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.category_incomes.CategoryIncomesViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.custom_category_dialog.CategoryDialogViewModel
import com.darleyleal.financebuddy.presenter.screens.home.HomeViewModel
import com.darleyleal.financebuddy.presenter.screens.insert.InsertViewModel
import com.darleyleal.financebuddy.presenter.screens.reports.report_expenses.ReportExpensesViewModel
import com.darleyleal.financebuddy.presenter.screens.reports.reports_incomes.ReportsIncomesViewModel
import com.darleyleal.financebuddy.presenter.screens.start.StarViewModel
import javax.inject.Inject

class NavigationProvider @Inject constructor(
    categoryExpensesViewModel: CategoryExpensesViewModel,
    categoryIncomesViewModel: CategoryIncomesViewModel,
    homeViewModel: HomeViewModel,
    reportsIncomesViewModel: ReportsIncomesViewModel,
    reportsExpensesViewModel: ReportExpensesViewModel,
    startViewModel: StarViewModel,
    insertViewModel: InsertViewModel,
    newCategoryViewModel: CategoryDialogViewModel
) : ViewModel() {

    private val viewModels: Map<ViewModelKey, ViewModel> = mapOf(
        ViewModelKey.CATEGORY_EXPENSES to categoryExpensesViewModel,
        ViewModelKey.CATEGORY_INCOMES to categoryIncomesViewModel,
        ViewModelKey.HOME to homeViewModel,
        ViewModelKey.REPORT_INCOMES to reportsIncomesViewModel,
        ViewModelKey.REPORTS_EXPENSES to reportsExpensesViewModel,
        ViewModelKey.START to startViewModel,
        ViewModelKey.INSERT to insertViewModel,
        ViewModelKey.NEW_CATEGORY to newCategoryViewModel
    )

    fun getViewModel(key: ViewModelKey): ViewModel {
        return viewModels[key] ?: throw IllegalArgumentException(
            "ViewModel não encontrada para a chave: $key"
        )
    }
}