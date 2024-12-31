package com.darleyleal.financebuddy.presenter.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.domain.navigation.bottonNavigationItems
import com.darleyleal.financebuddy.presenter.components.TypeOptionsTopAppBar
import com.darleyleal.financebuddy.presenter.screens.categories.CategoriesScreen
import com.darleyleal.financebuddy.presenter.screens.categories.custom_category_dialog.CategoryDialogViewModel
import com.darleyleal.financebuddy.presenter.screens.categories.custom_category_dialog.CategoryDialog
import com.darleyleal.financebuddy.presenter.screens.home.HomeScreen
import com.darleyleal.financebuddy.presenter.screens.home.components.HomeScreenTopAppBar
import com.darleyleal.financebuddy.presenter.screens.reports.ReportScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigationProvider: NavigationProvider,
    selectedItemIndex: Int,
    onNavigatoToInsertScreen: () -> Unit,
    selectedItemIndexUpdate: (Int) -> Unit
) {
    var repostButtonWasClicked by remember { mutableStateOf(false) }
    var categoryButtonWasClicked by remember { mutableStateOf(false) }
    val showCategoryDialog = remember { mutableStateOf(false) }

    val categoryDialogViewModel = navigationProvider.getViewModel(
        ViewModelKey.NEW_CATEGORY
    ) as CategoryDialogViewModel

    var textFieldIsValid by remember { mutableStateOf(true) }
    val category by categoryDialogViewModel.category.collectAsState()
    val radioOptionSelected by categoryDialogViewModel.radioOptionSelected.collectAsState()
    val radioOptions = categoryDialogViewModel.radioOptionsList
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                when (selectedItemIndex) {
                    0 -> {
                        HomeScreenTopAppBar()
                    }

                    1 -> {
                        TypeOptionsTopAppBar(wasClicked = repostButtonWasClicked, onClick = {
                            repostButtonWasClicked = !repostButtonWasClicked
                        })
                    }

                    2 -> {
                        TypeOptionsTopAppBar(wasClicked = categoryButtonWasClicked, onClick = {
                            categoryButtonWasClicked = !categoryButtonWasClicked
                        })
                    }
                }
            })
        },
        floatingActionButton = {
            when (selectedItemIndex) {
                0 -> {
                    ExtendedFloatingActionButton(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(text = stringResource(id = R.string.new_registration))
                        },
                        onClick = {
                            onNavigatoToInsertScreen()
                        }
                    )
                }

                2 -> {
                    ExtendedFloatingActionButton(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(text = "New Category")
                        },
                        onClick = {
                            showCategoryDialog.value = !showCategoryDialog.value
                        }
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar {
                bottonNavigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndexUpdate(index)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                tint = contentColorFor(
                                    backgroundColor =
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        },
                        label = {
                            Text(text = item.label, fontWeight = FontWeight(700))
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors()
                    )
                }
            }
        }
    ) {
        when (selectedItemIndex) {
            0 -> {
                HomeScreen(
                    navigationProvider = navigationProvider,
                    paddingValues = it
                )
            }

            1 -> {
                ReportScreen(
                    navigationProvider = navigationProvider,
                    paddingValues = it
                )
            }

            2 -> {
                CategoriesScreen(
                    navigationProvider = navigationProvider,
                    paddingValues = it,
                    wasClicked = categoryButtonWasClicked
                )
            }
        }

        if (showCategoryDialog.value) {
            CategoryDialog(
                title = stringResource(R.string.add_new_category),
                textFieldIsValid = textFieldIsValid,
                textFieldUpdateValue = { text ->
                    textFieldIsValid = text.trim().isNotEmpty()
                    categoryDialogViewModel.updateCategoryField(text)
                },
                text = category,
                radioOptionSelected = { optionSelected ->
                    categoryDialogViewModel.updateRadioButton(optionSelected)
                },
                radioOptions = radioOptions,
                onDismiss = {
                    showCategoryDialog.value = false
                },
                categoryFunction = {
                    textFieldIsValid = categoryDialogViewModel.validateCategoryField(category)
                    when {
                        textFieldIsValid -> {
                            categoryDialogViewModel.insert(
                                name = category, type = radioOptionSelected
                            )
                            Toast.makeText(
                                context,
                                context.getString(
                                    R.string.this_category_was_save_successfully
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                            showCategoryDialog.value = false
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