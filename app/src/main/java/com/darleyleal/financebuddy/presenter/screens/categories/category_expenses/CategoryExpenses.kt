package com.darleyleal.financebuddy.presenter.screens.categories.category_expenses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presenter.screens.categories.components.CategorySection
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@Composable
fun CategoryExpenses(
    navigationProvider: NavigationProvider,
    modifier: Modifier = Modifier
) {
    FinanceBuddyTheme {
        val viewModel = navigationProvider.getViewModel(
            ViewModelKey.CATEGORY_EXPENSES
        ) as CategoryExpensesViewModel

        var showEditCategoryDialog by remember { mutableStateOf(false) }
        var categoryTextFieldIsValid by remember { mutableStateOf(false) }
        var removeItem by remember { mutableStateOf(false) }
        val listOfIncomes by viewModel.listAllExpenses.collectAsState()

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
                viewModel.getExpenseById(it)
            },
            onDeleteClickItem = {
                removeItem = !removeItem
                viewModel.getExpenseById(it)
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