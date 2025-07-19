package com.darleyleal.financebuddy.presetation.screens.insert

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.presetation.components.CustomTextField
import com.darleyleal.financebuddy.presetation.components.DatePickerField
import com.darleyleal.financebuddy.presetation.components.TypeRegistration
import com.darleyleal.financebuddy.presetation.navigation.NavigationProvider
import com.darleyleal.financebuddy.presetation.theme.FinanceBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InsertScreen(
    modifier: Modifier = Modifier,
    indice: Int,
    navigationProvider: NavigationProvider,
    paddingValues: PaddingValues,
    onPopBackStack: () -> Unit = {},
    onNavigateToCameraScreen: () -> Unit,
    onNavigateToCategoryScreen: () -> Unit
) {
    val viewModel = navigationProvider.getViewModel(ViewModelKey.INSERT) as InsertViewModel

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var validateFields by remember { mutableStateOf(true) }
    val uiState by viewModel.uiState.collectAsState()

    FinanceBuddyTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.new_registration),
                            fontSize = 24.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onPopBackStack()
                            viewModel.clearFields()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            validateFields = viewModel.validateFormFields()
                            when {
                                validateFields -> {
                                    viewModel.insertRegistration()
                                    Toast.makeText(
                                        context,
                                        context.getString(
                                            R.string.the_registration_was_saved_successfully
                                        ),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onPopBackStack()
                                }

                                viewModel.uiState.value.selectedType.isEmpty() -> {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.click_to_confirm_category),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.incorrect_data_please_check_and_try_again),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                        .imePadding()
                        .navigationBarsPadding()
                ) {
                    Spacer(modifier = modifier.padding(8.dp))

                    CustomTextField(
                        title = stringResource(R.string.name),
                        text = uiState.name,
                        icon = Icons.Filled.Edit,
                        singleLine = true,
                        fieldIsValidate = validateFields || viewModel.validateFormFields(),
                        updateTextValue = {
                            viewModel.updateName(it)
                        }
                    )

                    CustomTextField(
                        title = stringResource(R.string.description),
                        text = uiState.description,
                        icon = Icons.Filled.Description, singleLine = false,
                        fieldIsValidate = validateFields || viewModel.validateFormFields(),
                        updateTextValue = {
                            viewModel.updateDescription(it)
                        }
                    )

                    CustomTextField(
                        title = stringResource(R.string.value),
                        text = uiState.value,
                        icon = Icons.Filled.AttachMoney, singleLine = true,
                        fieldIsValidate = validateFields || viewModel.validateFormFields(),
                        updateTextValue = {
                            viewModel.updateValue(it)
                        }
                    )

                    DatePickerField(
                        title = stringResource(R.string.date),
                        text = uiState.date,
                        fieldIsValidate = validateFields || viewModel.validateFormFields(),
                        icon = Icons.Filled.DateRange,
                        toUpdateDatePickerField = {
                            viewModel.updateDate(it)
                        }
                    )

                    when (indice) {
                        0 -> {
                            when {
                                uiState.incomes.isNotEmpty() -> {
                                    TypeRegistration(
                                        listOfCategory = uiState.incomes,
                                        optionSelected = {
                                            viewModel.updateSelectedType(it)
                                        },
                                        title = stringResource(id = R.string.select_a_income_type)
                                    )
                                    viewModel.selectCategory(stringResource(id = R.string.income))
                                }

                                else -> {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            modifier = modifier
                                                .padding(
                                                    horizontal = 8.dp,
                                                    vertical = 8.dp
                                                )
                                                .fillMaxWidth(),
                                            fontWeight = FontWeight.W700,
                                            text = stringResource(R.string.oops),
                                            fontSize = 38.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color.Red
                                        )

                                        Text(
                                            modifier = modifier
                                                .padding(horizontal = 34.dp)
                                                .fillMaxWidth(),
                                            text = stringResource(R.string.no_categories_to_list),
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Justify,
                                            color = when {
                                                isSystemInDarkTheme() -> Color.Cyan
                                                else -> Color.Black
                                            }
                                        )

                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(32.dp)
                                                .height(62.dp),
                                            onClick = {
                                                onNavigateToCategoryScreen()
                                            }) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Add,
                                                    contentDescription = null
                                                )

                                                Spacer(modifier = Modifier.padding(4.dp))

                                                Text(
                                                    text = "Add",
                                                    fontSize = 18.sp,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        1 -> {
                            when {
                                uiState.expenses.isNotEmpty() -> {
                                    TypeRegistration(
                                        listOfCategory = uiState.expenses,
                                        optionSelected = {
                                            viewModel.updateSelectedType(it)
                                        },
                                        title = stringResource(id = R.string.select_a_expense_type)
                                    )
                                    viewModel.selectCategory(stringResource(id = R.string.expense))
                                }

                                else -> {
                                    Text(
                                        modifier = modifier
                                            .padding(
                                                horizontal = 8.dp,
                                                vertical = 8.dp
                                            )
                                            .fillMaxWidth(),
                                        fontWeight = FontWeight.W700,
                                        text = stringResource(R.string.oops),
                                        fontSize = 38.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.Red
                                    )
                                    Text(
                                        modifier = modifier
                                            .padding(horizontal = 34.dp)
                                            .fillMaxWidth(),
                                        text = stringResource(R.string.no_categories_to_list),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Justify,
                                        color = when {
                                            isSystemInDarkTheme() -> Color.Cyan
                                            else -> Color.Black
                                        }
                                    )

                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(32.dp)
                                            .height(62.dp),
                                        onClick = {
                                            onNavigateToCategoryScreen()
                                        }) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = null
                                            )

                                            Spacer(modifier = Modifier.padding(4.dp))

                                            Text(
                                                text = "Add",
                                                fontSize = 18.sp,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {
                Button(
                    modifier = Modifier.fillMaxWidth().padding(102.dp).height(62.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    onClick =  {
                        onNavigateToCameraScreen()
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ViewInAr,
                            contentDescription = null,
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            text = "Use AI",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
        )
    }
}