package com.darleyleal.financebuddy.presenter.screens.categories.category_incomes

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.screens.categories.components.CategoryItemCard
import com.darleyleal.financebuddy.presenter.screens.categories.components.EditCustDialog
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
        val listOfIncomes by viewModel.listAllIncomes.collectAsState()

        val category by viewModel.category.collectAsState()
        val name by viewModel.name.collectAsState()

        val context = LocalContext.current

        LaunchedEffect(category?.id) {
            viewModel.updateNameField(category?.name.toString())
        }

        Column(
            modifier = modifier.padding(
                top = 16.dp,
                start = 16.dp, end = 16.dp, bottom = 90.dp
            )
        ) {
            LazyColumn {
                items(listOfIncomes) {
                    CategoryItemCard(title = it.name,
                        onClickEditCategory = {
                            showEditCategoryDialog = !showEditCategoryDialog
                            viewModel.getIncomeById(it.id)
                        }
                    )
                }
            }
            if (showEditCategoryDialog) {
                EditCustDialog(
                    title = stringResource(R.string.update_category),
                    textFieldIsValid = categoryTextFieldIsValid,
                    textFieldUpdateValue = {
                        categoryTextFieldIsValid = it.trim().isNotEmpty()
                        viewModel.updateNameField(it)
                    },
                    onDismiss = {
                        showEditCategoryDialog = false
                    },
                    text = name,
                    editCategoryField = {
                        categoryTextFieldIsValid = viewModel.validateField(name)
                        when {
                            categoryTextFieldIsValid -> {
                                viewModel.update(name = name)
                                Toast.makeText(
                                    context,
                                    context.getString(
                                        R.string.this_category_was_save_successfully
                                    ),
                                    Toast.LENGTH_SHORT
                                ).show()
                                showEditCategoryDialog = false
                            }

                            else -> {
                                Toast.makeText(
                                    context,
                                    context.getString(
                                        R.string.incorrect_data_please_check_and_try_again
                                    ),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
            }
        }
    }
}