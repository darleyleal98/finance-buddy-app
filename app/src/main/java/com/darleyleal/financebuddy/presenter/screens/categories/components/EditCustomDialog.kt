package com.darleyleal.financebuddy.presenter.screens.categories.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.presenter.screens.insert.components.CustomTextField

@Composable
fun EditCustDialog(
    title: String,
    textFieldIsValid: Boolean,
    textFieldUpdateValue: (String) -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    editCategoryField: () -> Unit
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
            }
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    editCategoryField()
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