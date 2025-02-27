package com.darleyleal.financebuddy.presetation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.financebuddy.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun <T> CategoryModalBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    textFieldIsValid: Boolean,
    textFieldUpdateValue: (String) -> Unit,
    text: String,
    radioOptionSelected: (String) -> Unit,
    radioOptions: List<T>,
    showModalBotomSheet: (Boolean) -> Unit,
    saveCategory: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val statusBarPadding = WindowInsets.statusBars.getTop(LocalDensity.current)
    val context = LocalContext.current
    var verifyIfRadioOptionWasSelected by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxHeight()
                .padding(top = statusBarPadding.dp),
            sheetState = sheetState,
            onDismissRequest = {
                showModalBotomSheet(false)
            }
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = title,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            Column {
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
                Text(
                    modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                    text = stringResource(R.string.click_to_confirm_category),
                    fontWeight = FontWeight.W700
                )
                RadioButtonSelection(
                    optionSelected = {
                        verifyIfRadioOptionWasSelected = true
                        radioOptionSelected(it)
                    },
                    radioButtons = radioOptions
                )
            }

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
                                showModalBotomSheet(false)
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
                        when {
                            text.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.the_category_field_is_required),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            !verifyIfRadioOptionWasSelected -> {
                                Toast.makeText(
                                    context,
                                    context.getString(
                                        R.string.the_category_is_required_click_to_confirm
                                    ),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                saveCategory()
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