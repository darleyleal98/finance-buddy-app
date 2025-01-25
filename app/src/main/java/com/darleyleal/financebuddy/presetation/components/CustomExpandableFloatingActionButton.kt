package com.darleyleal.financebuddy.presetation.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R

data class FABItem(
    val icon: ImageVector,
    val text: String
)

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun CustomExpandableFloatingActionButton(
    modifier: Modifier = Modifier,
    items: List<FABItem>,
    fabButton: FABItem = FABItem(
        icon = Icons.Rounded.Add,
        text = stringResource(R.string.new_registration)
    ),
    onItemClick: (FABItem) -> Unit
) {
    var buttonClicked by remember { mutableStateOf(false) }
    val interactionSource = MutableInteractionSource()

    Card(
        modifier, elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column {
            AnimatedVisibility(
                visible = buttonClicked,
                enter = expandVertically(tween(500)) + fadeIn(),
                exit = shrinkVertically(tween(200)) + fadeOut(
                    animationSpec = tween(100)
                )
            ) {
                Column(
                    modifier = modifier
                        .padding(vertical = 15.dp, horizontal = 20.dp)
                ) {
                    items.forEach { item ->
                        Row(
                            modifier = modifier.padding(vertical = 10.dp)
                        ) {
                            TextButton(
                                onClick = { onItemClick(item) }) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null
                                )
                                Spacer(modifier = modifier.width(15.dp))
                                Text(
                                    text = item.text, fontSize = 18.sp,
                                    color = when {
                                        isSystemInDarkTheme() -> Color.Cyan
                                        else -> Color.Black
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Card(
                modifier = modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        buttonClicked = !buttonClicked
                    }
                ),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = modifier.padding(vertical = 15.dp, horizontal = 20.dp)
                ) {
                    Icon(imageVector = fabButton.icon, contentDescription = null)

                    AnimatedVisibility(
                        visible = buttonClicked,
                        enter = expandVertically(animationSpec = tween(500)) + fadeIn(),
                        exit = shrinkVertically(tween(200)) + fadeOut(tween(200))
                    ) {
                        Row(modifier = modifier) {
                            Spacer(modifier = modifier.width(20.dp))
                            Text(
                                text = fabButton.text,
                                textAlign = TextAlign.Center,
                                modifier = modifier.offset(y = 3.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}