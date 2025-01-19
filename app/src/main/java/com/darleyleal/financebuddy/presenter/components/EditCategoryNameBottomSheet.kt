package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryNameBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    textFieldIsValid: Boolean,
    textFieldUpdateValue: (String) -> Unit,
    text: String,
    showModalBottomSheet: (Boolean) -> Unit,
    onSaveCategory: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val statusBarPadding = WindowInsets.statusBars.getTop(LocalDensity.current)
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxHeight()
                .padding(top = statusBarPadding.dp),
            sheetState = sheetState,
            onDismissRequest = {
                showModalBottomSheet(false)
            }
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = title,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            CustomTextField(
                title = stringResource(id = R.string.category),
                text = text,
                icon = Icons.Filled.Edit,
                singleLine = true,
                updateTextValue = {
                    textFieldUpdateValue(it)
                },
                fieldIsValidate = textFieldIsValid
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    modifier = modifier
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .size(width = 200.dp, height = 46.dp),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showModalBottomSheet(false)
                            }
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        fontSize = 22.sp
                    )
                }

                Button(
                    modifier = modifier
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .size(width = 200.dp, height = 46.dp),
                    onClick = {
                        onSaveCategory()
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showModalBottomSheet(false)
                            }
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}