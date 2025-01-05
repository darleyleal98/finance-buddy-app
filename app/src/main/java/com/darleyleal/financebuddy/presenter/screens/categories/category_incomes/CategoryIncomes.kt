package com.darleyleal.financebuddy.presenter.screens.categories.category_incomes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.components.CategorySection
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@Composable
fun CategoryIncomes(
    navigationProvider: NavigationProvider,
    modifier: Modifier = Modifier
) {
    FinanceBuddyTheme {
        val viewModel = navigationProvider.getViewModel(
            ViewModelKey.CATEGORY_INCOMES
        ) as CategoryIncomesViewModel

        var showEditCategoryDialog by remember { mutableStateOf(false) }
        var categoryTextFieldIsValid by remember { mutableStateOf(false) }
        var removeItem by remember { mutableStateOf(false) }
        val listOfIncomes by viewModel.listAllIncomes.collectAsState()

        val category by viewModel.category.collectAsState()
        val name by viewModel.name.collectAsState()

        val context = LocalContext.current

        LaunchedEffect(category?.id) {
            viewModel.updateNameField(category?.name.toString())
        }

        CategorySection(
            modifier = modifier,
            name = name,
            listOfCategories = listOfIncomes,
            onClickEditCategory = {
                showEditCategoryDialog = !showEditCategoryDialog
                viewModel.getIncomeById(it)
            },
            onDeleteClickItem = {
                removeItem = !removeItem
                viewModel.getIncomeById(it)
            },
            isValid = categoryTextFieldIsValid,
            textFieldIsValid = {
                categoryTextFieldIsValid = it.trim().isNotEmpty()
                viewModel.updateNameField(it)
            },
            showEditCategoryDialog = { showEditCategoryDialog = it },
            context = context,
            updateName = { viewModel.update(it) },
            removeItem = removeItem,
            onDismissIRemoveItem = { removeItem = !removeItem },
            deleteThisItem = { viewModel.delete() }
        )
    }
}