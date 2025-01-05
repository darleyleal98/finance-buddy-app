package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R

@Composable
fun ItemsNotFound(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .size(500.dp, 300.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Icon(
            imageVector = Icons.Filled.SearchOff,
            contentDescription = null,
            tint = when {
                isSystemInDarkTheme() -> {
                    Color.Cyan
                }

                else -> {
                    Color.Black
                }
            },
            modifier = modifier.size(50.dp)
        )
        Text(
            text = stringResource(R.string.oops_no_items_to_list),
            fontSize = 33.sp,
            textAlign = TextAlign.Center
        )
    }
}