package com.darleyleal.financebuddy.presenter.components

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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.darleyleal.financebuddy.data.local.Balance
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.usercases.utils.convertToCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardInformation(
    modifier: Modifier = Modifier,
    onClickVisibilityButton: () -> Unit,
    valuesIsVisible: Boolean,
    balance: Balance?,
    income: String,
    expanse: String
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        border = BorderStroke(2.dp, Color.Cyan),
        modifier = modifier
            .fillMaxWidth()
            .height(225.dp)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme())
                MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.inverseOnSurface
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
                balance?.let {
                    Text(
                        text = if (valuesIsVisible) convertToCurrency(it.availableBalance) else "*****",
                        fontWeight = FontWeight.W700,
                        fontSize = 42.sp,
                        color = if (isSystemInDarkTheme()) Color.Cyan else Color.Black,
                        modifier = modifier.padding(top = 8.dp)
                    )
                }

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