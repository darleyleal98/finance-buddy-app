package com.darleyleal.financebuddy.presenter.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryInformations(
    registration: Registration,
    modifier: Modifier = Modifier,
    showDeleteDialog: (Boolean) -> Unit,
    onDeleteItem: (Registration) -> Unit,
    registrationId: (Long) -> Unit,
    showUpdateRegistrationButtonSheet: (Boolean) -> Unit,
    openInFullModalSheetButton: (Boolean, Registration) -> Unit
) {
    var expand by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable(
                onClick = {
                    expand = !expand
                }
            ),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.primaryContainer.copy(
                alpha = 0.0f
            )
        )
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = registration.name.lowercase().replaceFirstChar { it.titlecase() },
                    color = when {
                        isSystemInDarkTheme() -> Color.Cyan
                        else -> Color.Black
                    },
                    fontSize = 24.sp, fontWeight = FontWeight.W700
                )
                Text(text = convertDate(registration.date))
                Row(
                    modifier = modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = when {
                            registration.category == Type.Income.name -> Icons.AutoMirrored.Default.TrendingUp
                            else -> Icons.AutoMirrored.Default.TrendingDown
                        },
                        tint = when {
                            registration.category == Type.Income.name -> Color.Green
                            else -> Color.Red
                        },
                        contentDescription = null
                    )
                    Text(
                        text = convertToCurrency(registration.value),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700,
                    )
                }

                AnimatedVisibility(expand) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            onClick = {
                                showUpdateRegistrationButtonSheet(true)
                                registrationId(registration.id)
                            }
                        ) {
                            Icon(Icons.Filled.Edit, null)
                        }

                        IconButton(
                            onClick = {
                                onDeleteItem(registration)
                                showDeleteDialog(true)
                            }
                        ) {
                            Icon(Icons.Filled.Delete, null)
                        }

                        IconButton(
                            onClick = {
                                openInFullModalSheetButton(true, registration)
                            }
                        ) {
                            Icon(Icons.Filled.OpenInFull, null)
                        }
                    }
                }
            }
        }
    }
}