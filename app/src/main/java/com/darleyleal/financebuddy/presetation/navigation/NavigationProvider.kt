package com.darleyleal.financebuddy.presetation.navigation

import androidx.lifecycle.ViewModel
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presetation.screens.analytics.AnalyticsViewModel
import com.darleyleal.financebuddy.presetation.screens.categories.category_expenses.CategoryExpensesViewModel
import com.darleyleal.financebuddy.presetation.screens.categories.category_incomes.CategoryIncomesViewModel
import com.darleyleal.financebuddy.presetation.screens.categories.CategoryViewModel
import com.darleyleal.financebuddy.presetation.screens.home.card_information.CardInformationViewModel
import com.darleyleal.financebuddy.presetation.screens.home.HomeViewModel
import com.darleyleal.financebuddy.presetation.screens.insert.InsertViewModel
import com.darleyleal.financebuddy.presetation.screens.start.StarViewModel
import javax.inject.Inject

class NavigationProvider @Inject constructor(
    categoryExpensesViewModel: CategoryExpensesViewModel,
    categoryIncomesViewModel: CategoryIncomesViewModel,
    homeViewModel: HomeViewModel,
    analyticsViewModel: AnalyticsViewModel,
    startViewModel: StarViewModel,
    insertViewModel: InsertViewModel,
    newCategoryViewModel: CategoryViewModel,
    balanceViewModel: CardInformationViewModel,
) : ViewModel() {

    private val viewModels: Map<ViewModelKey, ViewModel> = mapOf(
        ViewModelKey.CATEGORY_EXPENSES to categoryExpensesViewModel,
        ViewModelKey.CATEGORY_INCOMES to categoryIncomesViewModel,
        ViewModelKey.HOME to homeViewModel,
        ViewModelKey.ANALYTICS to analyticsViewModel,
        ViewModelKey.START to startViewModel,
        ViewModelKey.INSERT to insertViewModel,
        ViewModelKey.NEW_CATEGORY to newCategoryViewModel,
        ViewModelKey.BALANCE to balanceViewModel,
    )

    fun getViewModel(key: ViewModelKey): ViewModel {
        return viewModels[key] ?: throw IllegalArgumentException(
            "ViewModel não encontrada para a chave: $key"
        )
    }
}