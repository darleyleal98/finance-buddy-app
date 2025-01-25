package com.darleyleal.financebuddy.presetation.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.utils.toBrazilianDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    icon: ImageVector,
    fieldIsValidate: Boolean,
    toUpdateDatePickerField: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePickerDialog = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            toUpdateDatePickerField(it.toBrazilianDateFormat())
                        }
                        showDatePickerDialog = false
                    }
                ) {
                    Text(text = stringResource(R.string.confirm).uppercase())
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    CustomTextField(title = title, text = text,
        singleLine = true, icon = icon, fieldIsValidate = fieldIsValidate, updateTextValue = {
            toUpdateDatePickerField(it)
        }, focusEvent = {
            if (it.isFocused) {
                showDatePickerDialog = true
                focusManager.clearFocus(force = true)
            }
        }
    )
}

