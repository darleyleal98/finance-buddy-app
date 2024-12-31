package com.darleyleal.financebuddy.presenter.screens.categories.custom_category_dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.presenter.components.RadioButtonSingleSelection
import com.darleyleal.financebuddy.presenter.screens.insert.components.CustomTextField

@Composable
fun CategoryDialog(
    title: String,
    textFieldIsValid: Boolean,
    textFieldUpdateValue: (String) -> Unit,
    text: String,
    radioOptionSelected: (String) -> Unit,
    radioOptions: List<String>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    categoryFunction: () -> Unit
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                CustomTextField(
                    title = stringResource(id = R.string.category),
                    text = text,
                    icon = Icons.Filled.Edit,
                    singleLine = true,
                    updateTextValue = {
                        textFieldUpdateValue(it)
                    },
                    fieldIsValidate = textFieldIsValid
                )
                Spacer(modifier = modifier.padding(top = 4.dp))
                RadioButtonSingleSelection(
                    paddingValue = 0.dp,
                    radioButtonSelected = {
                        radioOptionSelected(it)
                    },
                    radioButtons = radioOptions
                )
            }
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    categoryFunction()
                }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "Dismiss")
            }
        }
    )
}