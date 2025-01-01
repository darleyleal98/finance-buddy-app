package com.darleyleal.financebuddy.presenter.screens.insert

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.enums.ViewModelKey
import com.darleyleal.financebuddy.domain.navigation.NavigationProvider
import com.darleyleal.financebuddy.presenter.components.CustomTextField
import com.darleyleal.financebuddy.presenter.components.RadioButtonSingleSelection
import com.darleyleal.financebuddy.presenter.screens.insert.components.DatePickerField
import com.darleyleal.financebuddy.presenter.theme.FinanceBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InsertScreen(
    modifier: Modifier = Modifier,
    navigationProvider: NavigationProvider,
    paddingValues: PaddingValues,
    onPopBackStack: () -> Unit = {},
) {
    val viewModel = navigationProvider.getViewModel(ViewModelKey.INSERT) as InsertViewModel

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var validateFields by remember { mutableStateOf(true) }

    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val value by viewModel.value.collectAsState()
    val date by viewModel.date.collectAsState()
    val radioOptionSelected by viewModel.radioOptionSelected.collectAsState()
    val radioOptionsList by viewModel.radioOptionsList.collectAsState()

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
                            viewModel.cleanFields()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
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
                ) {
                    Spacer(modifier = modifier.padding(8.dp))
                    CustomTextField(title = "Name",
                        text = name,
                        icon = Icons.Filled.Edit,
                        singleLine = true,
                        fieldIsValidate = validateFields || viewModel.validateField(name),
                        updateTextValue = {
                            viewModel.updateNameTextField(it)
                        }
                    )
                    CustomTextField(title = "Description",
                        text = description,
                        icon = Icons.Filled.Description, singleLine = false,
                        fieldIsValidate = validateFields || viewModel.validateField(description),
                        updateTextValue = {
                            viewModel.updateDescriptionTextField(it)
                        }
                    )
                    CustomTextField(title = "Value",
                        text = value,
                        icon = Icons.Filled.AttachMoney, singleLine = true,
                        fieldIsValidate = validateFields || viewModel.validateField(value),
                        updateTextValue = {
                            viewModel.updateValueTextField(it)
                        }
                    )
                    DatePickerField(
                        title = "Date",
                        text = date,
                        fieldIsValidate = validateFields || viewModel.validateField(date),
                        icon = Icons.Filled.DateRange,
                        toUpdateDatePickerField = {
                            viewModel.updateDateTextField(it)
                        }
                    )
                    Spacer(modifier = modifier.padding(top = 16.dp))
                    Button(
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .size(142.dp, 44.dp),
                        onClick = {
                            validateFields = viewModel.validateFormFields()
                            when {
                                validateFields -> {
                                    viewModel.insert(
                                        name,
                                        description,
                                        value,
                                        date,
                                        radioOptionSelected
                                    )
                                    Toast.makeText(
                                        context,
                                        context.getString(
                                            R.string.the_registration_was_saved_successfully
                                        ),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onPopBackStack()
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
                        Text(
                            text = stringResource(
                                R.string.save
                            ).uppercase(), fontSize = 22.sp
                        )
                    }
                }
            }
        )
    }
}