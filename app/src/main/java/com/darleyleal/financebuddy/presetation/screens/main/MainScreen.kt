package com.darleyleal.financebuddy.presetation.screens.main

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Wallet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presetation.navigation.NavigationProvider
import com.darleyleal.financebuddy.presetation.navigation.bottonNavigationItems
import com.darleyleal.financebuddy.presetation.components.CategoryModalBottomSheet
import com.darleyleal.financebuddy.presetation.components.CustomExpandableFloatingActionButton
import com.darleyleal.financebuddy.presetation.components.FABItem
import com.darleyleal.financebuddy.presetation.components.HomeScreenTopAppBar
import com.darleyleal.financebuddy.presetation.components.TypeOptionsTopAppBar
import com.darleyleal.financebuddy.presetation.components.UpdateBalanceDialog
import com.darleyleal.financebuddy.presetation.screens.analytics.AnalyticsScreen
import com.darleyleal.financebuddy.presetation.screens.categories.CategoryScreen
import com.darleyleal.financebuddy.presetation.screens.categories.CategoryViewModel
import com.darleyleal.financebuddy.presetation.screens.home.HomeScreen
import com.darleyleal.financebuddy.presetation.screens.home.card_information.CardInformationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigationProvider: NavigationProvider,
    selectedItemIndex: Int,
    onNavigateToInsertScreenWithIndice: (Int) -> Unit,
    selectedItemIndexUpdate: (Int) -> Unit
) {
    val categoryDialogViewModel = navigationProvider.getViewModel(
        ViewModelKey.NEW_CATEGORY
    ) as CategoryViewModel

    val balanceViewModel = navigationProvider.getViewModel(
        ViewModelKey.BALANCE
    ) as CardInformationViewModel

    val uiState by balanceViewModel.uiState.collectAsState()
    var validateBalanceField by remember { mutableStateOf(true) }
    var categoryButtonWasClicked by remember { mutableStateOf(false) }

    var showCategoryModalBottonSheet by remember { mutableStateOf(false) }
    val showBalanceDialog = remember { mutableStateOf(false) }

    val textFieldIsValid by remember { mutableStateOf(true) }
    val category by categoryDialogViewModel.category.collectAsState()
    val radioOptionSelected by categoryDialogViewModel.radioOptionSelected.collectAsState()
    val radioOptions = categoryDialogViewModel.radioOptionsList
    val context = LocalContext.current

    val itemsList = listOf(
        FABItem(
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            text = stringResource(R.string.income)
        ),
        FABItem(
            icon = Icons.AutoMirrored.Filled.TrendingDown,
            text = stringResource(R.string.expense)
        ),
        FABItem(icon = Icons.Default.Wallet, text = stringResource(R.string.balance))
    )

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                when (selectedItemIndex) {
                    0 -> {
                        HomeScreenTopAppBar()
                    }

                    1 -> {
                        Row {
                            Text(
                                text = stringResource(R.string.analytics),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W500,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                textAlign = TextAlign.Center,
                                color = when {
                                    isSystemInDarkTheme() -> Color.Cyan
                                    else -> Color.Black
                                }
                            )
                        }
                    }

                    2 -> {
                        TypeOptionsTopAppBar(wasClicked = categoryButtonWasClicked,
                            onClick = {
                                categoryButtonWasClicked = !categoryButtonWasClicked
                            }
                        )
                    }
                }
            })
        },
        floatingActionButton = {
            when (selectedItemIndex) {
                0 -> {
                    CustomExpandableFloatingActionButton(
                        items = itemsList,
                        onItemClick = {
                            when (it.text) {
                                Type.Income.name -> {
                                    onNavigateToInsertScreenWithIndice(0)
                                }

                                Type.Expense.name -> {
                                    onNavigateToInsertScreenWithIndice(1)
                                }

                                else -> {
                                    showBalanceDialog.value = !showBalanceDialog.value
                                }
                            }
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
                            Text(text = stringResource(R.string.new_category))
                        },
                        onClick = {
                            showCategoryModalBottonSheet = true
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
    ) { it ->
        when (selectedItemIndex) {
            0 -> {
                HomeScreen(
                    navigationProvider = navigationProvider,
                    paddingValues = it
                )
            }

            1 -> {
                AnalyticsScreen(
                    navigationProvider = navigationProvider,
                    paddingValues = it
                )
            }

            2 -> {
                CategoryScreen(
                    navigationProvider = navigationProvider,
                    paddingValues = it,
                    wasClicked = categoryButtonWasClicked
                )
            }
        }

        if (showCategoryModalBottonSheet) {
            CategoryModalBottomSheet(
                title = stringResource(R.string.new_category),
                textFieldIsValid = textFieldIsValid,
                textFieldUpdateValue = { value ->
                    categoryDialogViewModel.updateCategoryField(value)
                },
                text = category,
                radioOptions = radioOptions,
                radioOptionSelected = { optionSelected ->
                    categoryDialogViewModel.updateRadioButton(optionSelected)
                },
                showModalBotomSheet = {
                    showCategoryModalBottonSheet = false
                },
                saveCategory = {
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
                            showCategoryModalBottonSheet = false
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

        if (showBalanceDialog.value) {
            UpdateBalanceDialog(
                title = stringResource(id = R.string.balance),
                text = uiState.value,
                textFieldUpdateValue = { newValue ->
                    balanceViewModel.updateBalanceValueField(newValue)
                },
                textFieldIsValid = uiState.value.isBlank() ||
                        balanceViewModel.validadeBalanceField(),
                updateBalanceField = {
                    validateBalanceField = balanceViewModel.validadeBalanceField()
                    when {
                        validateBalanceField -> {
                            balanceViewModel.updateBalance()
                            Toast.makeText(
                                context,
                                context.getString(
                                    R.string.the_balance_was_saved_successfully
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
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
                },
                onDimiss = {
                    showBalanceDialog.value = !showBalanceDialog.value
                }
            )
        }
    }
}