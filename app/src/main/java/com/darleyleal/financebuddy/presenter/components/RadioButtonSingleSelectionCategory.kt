package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.data.local.Category

@Composable
fun <T> RadioButtonSingleSelectionCategory(
    modifier: Modifier = Modifier,
    optionSelected: (String) -> Unit,
    radioButtons: List<T>
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtons[0]) }

    Column(modifier.selectableGroup()) {
        radioButtons.forEach { item ->
            Row(
                modifier = modifier
                    .selectable(
                        selected = (item == selectedOption),
                        onClick = {
                            onOptionSelected(item)
                            optionSelected(
                                if (item is Category) item.name else item.toString()
                            )
                        },
                        role = Role.RadioButton
                    )
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (item == selectedOption),
                    onClick = null
                )
                Text(
                    text = if (item is Category) item.name else item.toString(),
                    fontSize = 18.sp,
                    modifier = modifier.padding(8.dp)
                )
            }
        }
    }
}