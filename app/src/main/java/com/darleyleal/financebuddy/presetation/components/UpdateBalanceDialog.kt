package com.darleyleal.financebuddy.presetation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R

@Composable
fun UpdateBalanceDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    textFieldUpdateValue: (String) -> Unit,
    textFieldIsValid: Boolean,
    updateBalanceField: () -> Unit,
    onDimiss: () -> Unit,
) {
    var verifyIfTextFieldIsEmpty by remember { mutableStateOf(false) }

    AlertDialog(
        title = {
            Text(
                text = title, modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                CustomTextField(
                    title = stringResource(id = R.string.balance),
                    text = text,
                    icon = Icons.Filled.Wallet,
                    singleLine = true,
                    updateTextValue = {
                        textFieldUpdateValue(it)
                    },
                    fieldIsValidate = textFieldIsValid
                )
                when {
                    verifyIfTextFieldIsEmpty -> {
                        Text(
                            modifier = modifier.padding(horizontal = 8.dp),
                            text = stringResource(R.string.this_field_is_required),
                            color = Color.Red,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        },
        onDismissRequest = {
            onDimiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    when {
                        text.isEmpty() -> {
                            verifyIfTextFieldIsEmpty = true
                        }

                        else -> {
                            updateBalanceField()
                            onDimiss()
                        }
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDimiss()
                }
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}