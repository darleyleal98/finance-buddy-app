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
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.data.local.Category
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.components.CategorySection
import com.darleyleal.financebuddy.presenter.components.EditCategoryNameBottomSheet
import com.darleyleal.financebuddy.presenter.components.RemoveItemDialog
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

        var showEditCategoryButtomSheet by remember { mutableStateOf(false) }
        var categoryToBeEdited by remember { mutableStateOf<Category?>(null) }
        var categoryNameIsValid by remember { mutableStateOf(false) }
        val categoryName by viewModel.name.collectAsState()

        var showRemoveDialog by remember { mutableStateOf(false) }
        var categoryToBeRemoved by remember { mutableStateOf<Category?>(null) }

        val listOfIncomes by viewModel.listAllIncomes.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(categoryToBeEdited?.id) {
            categoryToBeEdited?.let { category ->
                viewModel.updateNameField(category.name)
            }
            categoryNameIsValid = viewModel.validateField(categoryName)
        }

        CategorySection(
            modifier = modifier,
            categoriesList = listOfIncomes,
            editCategory = { item, state ->
                categoryToBeEdited = item
                showEditCategoryButtomSheet = state
            },
            deleteItem = { item, state ->
                categoryToBeRemoved = item
                showRemoveDialog = state
            }
        )

        when {
            showRemoveDialog -> {
                RemoveItemDialog(
                    onDismissRequest = {
                        showRemoveDialog = false
                    },
                    onConfirmation = {
                        categoryToBeRemoved?.let { viewModel.delete(it) }
                    },
                    dialogTitle = context.getString(R.string.are_you_sure_about_this),
                    dialogText = context.getString(R.string.delete_item)
                )
            }

            showEditCategoryButtomSheet -> {
                categoryToBeEdited?.let { category ->
                    EditCategoryNameBottomSheet(
                        modifier = modifier,
                        title = context.getString(R.string.update_category),
                        textFieldIsValid = viewModel.validateField(category.name),
                        textFieldUpdateValue = { text ->
                            viewModel.updateNameField(text)
                        },
                        text = categoryName,
                        showModalBottomSheet = {
                            showEditCategoryButtomSheet = !showEditCategoryButtomSheet
                        },
                        onSaveCategory = {
                            viewModel.update(category.id, categoryName, category.type)
                        }
                    )
                }
            }
        }
    }
}