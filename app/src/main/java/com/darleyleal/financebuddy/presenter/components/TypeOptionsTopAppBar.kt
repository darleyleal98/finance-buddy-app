package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.domain.enums.Type

@Composable
fun TypeOptionsTopAppBar(
    wasClicked: Boolean,
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            modifier = modifier.size(width = 112.dp, height = 40.dp),
            colors = when (wasClicked) {
                true -> {
                    ButtonDefaults.buttonColors(Color(color = 0xFF402F59))
                }

                false -> {
                    ButtonDefaults.buttonColors(Color.Transparent)
                }
            },
            onClick = {
                onClick()
            }) {
            Text(
                text = Type.Income.name, fontSize = 18.sp,
                color = when {
                    isSystemInDarkTheme() && wasClicked -> Color.Cyan
                    isSystemInDarkTheme() -> Color.White
                    !isSystemInDarkTheme() && wasClicked -> Color.Cyan
                    else -> Color.Black
                }
            )
        }
        Button(
            modifier = modifier.size(width = 112.dp, height = 40.dp),
            colors = when (wasClicked) {
                true -> {
                    ButtonDefaults.buttonColors(Color.Transparent)
                }

                false -> {
                    ButtonDefaults.buttonColors(Color(color = 0xFF402F59))
                }
            },
            onClick = {
                onClick()
            }) {
            Text(
                text = Type.Expense.name,
                fontSize = 18.sp,
                color = when {
                    isSystemInDarkTheme() && wasClicked -> Color.White
                    isSystemInDarkTheme() -> Color.Cyan
                    !isSystemInDarkTheme() && wasClicked -> Color.Black
                    else -> Color.Cyan
                }
            )
        }
    }
}