package com.darleyleal.financebuddy.presetation.screens.home.card_information

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.utils.convertToCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardInformation(
    modifier: Modifier = Modifier,
    onClickVisibilityButton: () -> Unit,
    valuesIsVisible: Boolean,
    availableBalance: Float,
    income: String,
    expanse: String
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        border = BorderStroke(2.dp, if (isSystemInDarkTheme()) Color.Cyan else Color.LightGray),
        modifier = modifier
            .fillMaxWidth()
            .height(225.dp)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Column(modifier = modifier.padding(top = 22.dp, start = 16.dp, end = 16.dp)) {
            Text(text = stringResource(R.string.available_balance))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = if (valuesIsVisible) convertToCurrency(availableBalance) else "*****",
                    fontWeight = FontWeight.W700,
                    fontSize = 42.sp,
                    color = when {
                        availableBalance < 0 -> Color.Red
                        else -> Color.Green
                    },
                    modifier = modifier.padding(top = 8.dp)
                )

                Icon(
                    modifier = modifier
                        .clickable(onClick = {
                            onClickVisibilityButton()
                        })
                        .padding(top = 8.dp)
                        .size(30.dp)
                        .align(Alignment.CenterVertically),
                    imageVector =
                    if (valuesIsVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) Color.Cyan else Color.Black,
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropUp, tint = Color.Green,
                        contentDescription = null, modifier = modifier.size(30.dp)
                    )
                    Text(
                        text = Type.Income.name, fontSize = 22.sp,
                        modifier = modifier.align(Alignment.CenterVertically)
                    )
                }
                Text(
                    text = if (valuesIsVisible) income else "*****",
                    fontSize = 22.sp,
                    modifier = modifier.align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown, tint = Color.Red,
                        contentDescription = null, modifier = modifier.size(30.dp)
                    )
                    Text(
                        text = Type.Expense.name, fontSize = 22.sp,
                        modifier = modifier.align(Alignment.CenterVertically)
                    )
                }
                Text(
                    text = if (valuesIsVisible) expanse else "*****", fontSize = 22.sp,
                    modifier = modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}