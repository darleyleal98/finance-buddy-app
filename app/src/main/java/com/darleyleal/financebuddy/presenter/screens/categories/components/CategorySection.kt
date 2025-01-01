package com.darleyleal.financebuddy.presenter.screens.categories.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.data.local.Category
import com.darleyleal.financebuddy.presenter.components.ItemsNotFound

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    listOfCategories: List<Category>,
    name: String,
    onClickEditCategory: (Long) -> Unit,
    onDeleteClickItem: (Long) -> Unit,
    isValid: Boolean,
    textFieldIsValid: (String) -> Unit,
    updateName: (String) -> Unit,
    showEditCategoryDialog: (Boolean) -> Unit,
    removeItem: Boolean,
    onDismissIRemoveItem: () -> Unit,
    deleteThisItem: () -> Unit,
    context: Context
) {
    Column(
        modifier = modifier.padding(
            top = 16.dp,
            start = 16.dp, end = 16.dp, bottom = 90.dp
        )
    ) {
        LazyColumn {
            when {
                listOfCategories.isNotEmpty() -> {
                    items(listOfCategories) {
                        CategoryItemCard(
                            title = it.name,
                            onClickEditCategory = {
                                onClickEditCategory(it.id)
                            },
                            onClickDeleteItem = {
                                onDeleteClickItem(it.id)
                            }
                        )
                    }
                }

                else -> {
                    item {
                        ItemsNotFound()
                    }
                }
            }
        }

        when {
            isValid -> {
                EditCustDialog(
                    title = stringResource(R.string.update_category),
                    textFieldIsValid = isValid,
                    textFieldUpdateValue = {
                        textFieldIsValid(it)
                    },
                    onDismiss = {
                        showEditCategoryDialog(false)
                    },
                    text = name,
                    editCategoryField = {
                        when {
                            isValid -> {
                                updateName(name)
                                Toast.makeText(
                                    context,
                                    context.getString(
                                        R.string.this_category_was_save_successfully
                                    ),
                                    Toast.LENGTH_SHORT
                                ).show()
                                showEditCategoryDialog(false)
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

        when {
            removeItem -> {
                DeleteCustomAlertDialog(
                    onDismiss = {
                        onDismissIRemoveItem()
                    },
                    deleteThisItem = {
                        deleteThisItem()
                    },
                    context = context
                )
            }
        }
    }
}