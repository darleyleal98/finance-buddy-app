package com.darleyleal.financebuddy.presenter.screens.insert.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    icon: ImageVector,
    singleLine: Boolean,
    updateTextValue: (String) -> Unit,
    fieldIsValidate: Boolean,
    focusEvent: (FocusState) -> Unit = {}
) {
    var textFieldIsValid by remember { mutableStateOf(true) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .onFocusEvent {
                focusEvent(it)
            },
        value = text,
        onValueChange = {
            updateTextValue(it)
            textFieldIsValid = it.trim().isNotEmpty()
        },
        label = {
            Text(text = title)
        },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = null)
        },
        supportingText = {
            if (!textFieldIsValid || !fieldIsValidate) {
                Text(
                    text = stringResource(R.string.this_field_is_required),
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        },
        trailingIcon = {
            if (!textFieldIsValid || !fieldIsValidate) {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
            if (text.isNotBlank()) {
                Icon(
                    modifier = modifier.clickable { updateTextValue("") },
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }
        },
        isError = !textFieldIsValid || !fieldIsValidate,
        singleLine = singleLine,
        keyboardOptions = when {
            title == "Value" -> KeyboardOptions(keyboardType = KeyboardType.Number)
            else -> {
                KeyboardOptions(keyboardType = KeyboardType.Text)
            }
        }
    )
}