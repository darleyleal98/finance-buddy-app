package com.darleyleal.financebuddy.presetation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R

@Composable
fun HomeScreenTopAppBar(modifier: Modifier = Modifier) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.financebuddyicon),
            contentDescription = null,
            modifier = modifier.size(40.dp),
            tint = Color(0xFF00EEFF)
        )
        Spacer(modifier = modifier.padding(4.dp))
        Text(
            modifier = modifier.align(Alignment.CenterVertically),
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight((700))
                    )
                ) {
                    append("Finance")
                }
                withStyle(
                    SpanStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight((400))
                    )
                ) {
                    append("Buddy")
                }
            }
        )
    }
}