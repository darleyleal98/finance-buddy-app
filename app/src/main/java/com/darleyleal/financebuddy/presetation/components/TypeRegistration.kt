package com.darleyleal.financebuddy.presetation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.data.models.Category

@Composable
fun TypeRegistration(
    modifier: Modifier = Modifier,
    listOfCategory: List<Category>,
    optionSelected: (String) -> Unit,
    title: String
) {
    Text(
        modifier = modifier.padding(all = 8.dp),
        text = title,
        fontSize = 22.sp
    )
    RadioButtonSelection(
        optionSelected = {
            optionSelected(it)
        },
        radioButtons = listOfCategory
    )
}