package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darleyleal.financebuddy.data.local.Category

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    categoriesList: List<Category>,
    editCategory: (Category, Boolean) -> Unit,
    deleteItem: (Category, Boolean) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        when {
            categoriesList.isNotEmpty() -> {
                items(categoriesList) { category ->
                    CategoryItemCard(
                        category = category,
                        onClickEditCategory = { item, state ->
                            editCategory(item, state)
                        },
                        onClickDeleteItem = { item, state ->
                            deleteItem(item, state)
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
}