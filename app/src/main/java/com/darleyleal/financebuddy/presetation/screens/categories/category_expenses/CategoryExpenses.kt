package com.darleyleal.financebuddy.presetation.screens.categories.category_expenses

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
import com.darleyleal.financebuddy.data.models.Category
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presetation.navigation.NavigationProvider
import com.darleyleal.financebuddy.presetation.components.CategorySection
import com.darleyleal.financebuddy.presetation.components.EditCategoryNameBottomSheet
import com.darleyleal.financebuddy.presetation.components.RemoveItemDialog
import com.darleyleal.financebuddy.presetation.theme.FinanceBuddyTheme

@Composable
fun CategoryExpenses(
    navigationProvider: NavigationProvider,
    modifier: Modifier = Modifier
) {
    FinanceBuddyTheme {
        val viewModel = navigationProvider.getViewModel(
            ViewModelKey.CATEGORY_EXPENSES
        ) as CategoryExpensesViewModel

        var showEditCategoryButtomSheet by remember { mutableStateOf(false) }
        var categoryToBeEdited by remember { mutableStateOf<Category?>(null) }
        val categoryName by viewModel.name.collectAsState()
        var categoryNameIsValid by remember { mutableStateOf(false) }

        var showRemoveDialog by remember { mutableStateOf(false) }
        var categoryToBeRemoved by remember { mutableStateOf<Category?>(null) }

        val listAllExpenses by viewModel.listAllExpenses.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(categoryToBeEdited?.id) {
            categoryToBeEdited?.let { category ->
                viewModel.updateNameField(category.name)
            }
            categoryNameIsValid = viewModel.validateField(categoryName)
        }

        CategorySection(
            modifier = modifier,
            categoriesList = listAllExpenses,
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