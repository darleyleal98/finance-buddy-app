package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.data.local.Registration
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.usercases.utils.convertDate
import com.darleyleal.financebuddy.domain.usercases.utils.convertToCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryInformations(registration: Registration, modifier: Modifier = Modifier) {
    var expand by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.primaryContainer.copy(
                alpha = 0.2f
            )
        ),
        onClick = {
            expand = !expand
        }
    ) {

        Row(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = registration.name,
                    color = when {
                        isSystemInDarkTheme() -> Color.Cyan
                        else -> Color.Black
                    },
                    fontSize = 24.sp, fontWeight = FontWeight.W700
                )
                Text(text = convertDate(registration.date))

                Row {
                    Icon(
                        imageVector = when {
                            registration.category == Type.Income.name -> Icons.Filled.ArrowDropUp
                            else -> Icons.Filled.ArrowDropDown
                        },
                        tint = when {
                            registration.category == Type.Income.name -> Color.Green
                            else -> Color.Red
                        },
                        contentDescription = null
                    )
                    Text(text = registration.category)
                }

                when {
                    expand -> {
                        Text(
                            modifier = modifier.padding(top = 22.dp),
                            text = registration.description
                        )
                        Text(
                            modifier = modifier.padding(top = 8.dp), text = registration.type
                        )
                    }
                }
            }
            Text(
                text = convertToCurrency(registration.value),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
        }
    }
}