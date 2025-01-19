package com.darleyleal.financebuddy.presenter.screens.home.update_registration

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.data.local.Registration
import com.darleyleal.financebuddy.presenter.components.CustomTextField
import com.darleyleal.financebuddy.presenter.components.DatePickerField
import com.darleyleal.financebuddy.presenter.screens.home.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateRegistrationModalBottomSheet(
    modifier: Modifier = Modifier,
    updateStateBottonSheet: (Boolean) -> Unit,
    onUpdateRegistrationClick: () -> Unit,
    showBottonSheet: Boolean,
    registration: Registration,
    viewModel: HomeViewModel
) {
    val statusBarPadding = WindowInsets.statusBars.getTop(LocalDensity.current)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val validateFields by remember { mutableStateOf(true) }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(registration.id) {
        viewModel.updateId(registration.id)
        viewModel.updateName(registration.name)
        viewModel.updateDescription(registration.description)
        viewModel.updateValue(registration.value.toString())
        viewModel.updateDate(registration.date)
        viewModel.updateCategory(registration.category)
        viewModel.updateType(registration.type)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            showBottonSheet -> {
                ModalBottomSheet(
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(top = statusBarPadding.dp),
                    sheetState = sheetState,
                    onDismissRequest = { updateStateBottonSheet(false) }
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = stringResource(R.string.update_registration),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = modifier.padding(top = 8.dp))

                    CustomTextField(title = stringResource(R.string.name),
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

                    CustomTextField(title = stringResource(R.string.value),
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

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            modifier = modifier
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .size(width = 225.dp, height = 46.dp),
                            onClick = {
                                scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        updateStateBottonSheet(false)
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.dismiss),
                                fontSize = 22.sp
                            )
                        }

                        Button(
                            modifier = modifier
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .size(width = 225.dp, height = 46.dp),
                            onClick = {
                                onUpdateRegistrationClick()
                                Toast.makeText(
                                    context,
                                    context.getString(
                                        R.string.the_registration_was_saved_successfully
                                    ),
                                    Toast.LENGTH_SHORT
                                ).show()
                                scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        updateStateBottonSheet(false)
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.save),
                                fontSize = 22.sp
                            )
                        }
                    }
                }
            }
        }
    }
}