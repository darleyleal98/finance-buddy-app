package com.darleyleal.financebuddy.presenter.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonSingleSelection(
    modifier: Modifier = Modifier,
    paddingValue: Dp = 8.dp,
    radioButtonSelected: (String) -> Unit,
    radioButtons: List<String>
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtons[0]) }

    Column(modifier.selectableGroup()) {
        radioButtons.forEach { text ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            radioButtonSelected(text)
                            Log.d("TAG", "RadioButtonSingleSelection: $text")
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = paddingValue),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = (text == selectedOption), onClick = null)
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifier.padding(16.dp)
                )
            }
        }
    }
}