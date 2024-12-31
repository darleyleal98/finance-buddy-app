package com.darleyleal.financebuddy.presenter.screens.categories.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryItemCard(
    modifier: Modifier = Modifier,
    title: String,
    onClickEditCategory: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(top = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(modifier = modifier.padding(start = 16.dp)) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(700),
                    color = if (isSystemInDarkTheme()) Color.Cyan else Color.Black
                )
            }
            Row(modifier = modifier.padding(end = 16.dp)) {
                Icon(
                    modifier = modifier.clickable {
                        onClickEditCategory()
                    },
                    imageVector = Icons.Filled.Edit, contentDescription = null
                )
                Spacer(modifier = modifier.padding(start = 22.dp))
                Icon(
                    modifier = modifier.clickable {

                    },
                    imageVector = Icons.Filled.Delete, contentDescription = null
                )
            }
        }
    }
    Divider()
}