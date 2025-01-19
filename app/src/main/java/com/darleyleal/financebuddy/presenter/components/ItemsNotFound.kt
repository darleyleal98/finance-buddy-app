package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R

@Composable
fun ItemsNotFound(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .size(500.dp, 400.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Image(
            painter = painterResource(R.drawable.not_found_illustration),
            contentDescription = null,
            modifier = modifier.size(150.dp)
        )
        Spacer(modifier = modifier.padding(top = 16.dp))
        Text(
            text = "Result Not Found",
            fontSize = 33.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.padding(top = 8.dp))
        Text(
            text = "Please try adding a new item so that it is listed on the screen",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}