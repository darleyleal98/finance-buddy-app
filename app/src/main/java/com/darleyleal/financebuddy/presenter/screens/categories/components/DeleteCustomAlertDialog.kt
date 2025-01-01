package com.darleyleal.financebuddy.presenter.screens.categories.components

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.darleyleal.financebuddy.R

@Composable
fun DeleteCustomAlertDialog(
    modifier: Modifier = Modifier,
    deleteThisItem: () -> Unit,
    onDismiss: () -> Unit,
    context: Context
) {
    AlertDialog(
        title = {
            Text(
                text = stringResource(
                    R.string.are_you_sure_about_this
                )
            )
        },
        text = {
            Text(
                text = stringResource(
                    R.string.delete_item
                )
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null
            )
        },
        onDismissRequest = {},
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = stringResource(R.string.dismiss))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                deleteThisItem()
                Toast.makeText(
                    context,
                    context.getString(R.string.this_item_was_deleted),
                    Toast.LENGTH_SHORT
                ).show()
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        }
    )
}