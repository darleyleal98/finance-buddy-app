package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.darleyleal.financebuddy.data.local.Registration
import com.darleyleal.financebuddy.domain.enums.Type
import com.darleyleal.financebuddy.domain.usercases.utils.convertDate
import com.darleyleal.financebuddy.domain.usercases.utils.convertToCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenInFullScreenRegistrationModalSheet(
    modifier: Modifier = Modifier,
    registration: Registration,
    showModalBottomSheet: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val statusBarPadding = WindowInsets.statusBars.getTop(LocalDensity.current)
    val context = LocalContext.current

    Column {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(top = statusBarPadding.dp),
            sheetState = sheetState,
            onDismissRequest = {
                showModalBottomSheet(false)
            }
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = when {
                        isSystemInDarkTheme() -> {
                            "https://i.pinimg.com/originals/31/53/2d/31532d7d378053de3b8bf23c6e7bfae3.gif"
                        }

                        else -> "https://i.pinimg.com/originals/2e/e6/99/2ee6998e34c3e2eff7b894c66cfc5267.jpg"
                    },
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .shadow(shape = RoundedCornerShape(36.dp), elevation = 1.dp),
                )

                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp),
                    text = registration.name,
                    fontSize = 36.sp,
                    color = when {
                        isSystemInDarkTheme() -> Color.Cyan
                        else -> Color.Black
                    },
                    textAlign = TextAlign.Center
                )
            }

            Row(modifier = modifier.padding(top = 32.dp, start = 16.dp)) {
                Icon(
                    modifier = modifier.size(18.dp),
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = null,
                    tint = when {
                        isSystemInDarkTheme() -> Color.Cyan
                        else -> Color.Black
                    }
                )
                Spacer(modifier = modifier.padding(4.dp))
                Text(text = convertDate(registration.date), fontSize = 18.sp)
            }

            Row(modifier = modifier.padding(top = 8.dp, start = 16.dp)) {
                Icon(
                    modifier = modifier.size(20.dp),
                    imageVector = Icons.Filled.Wallet,
                    contentDescription = null,
                    tint = when {
                        isSystemInDarkTheme() -> Color.Cyan
                        else -> Color.Black
                    }
                )
                Spacer(modifier = modifier.padding(4.dp))
                Text(text = convertToCurrency(registration.value), fontSize = 18.sp)
            }

            Row(modifier = modifier.padding(top = 8.dp, start = 16.dp)) {
                Icon(
                    modifier = modifier.size(20.dp),
                    imageVector = when {
                        registration.category == Type.Income.name -> {
                            Icons.AutoMirrored.Filled.TrendingUp
                        }

                        else -> {
                            Icons.AutoMirrored.Filled.TrendingDown
                        }
                    },
                    contentDescription = null,
                    tint = when {
                        registration.category == Type.Income.name -> {
                            Color.Green
                        }

                        else -> {
                            Color.Red
                        }
                    }
                )
                Spacer(modifier = modifier.padding(4.dp))
                Text(
                    text = "${registration.type.trim()}, ${registration.category}",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = modifier.padding(top = 22.dp))
            Text(
                modifier = modifier.padding(start = 16.dp),
                text = "Description",
                fontSize = 22.sp,
                fontWeight = FontWeight.W700
            )
            Spacer(modifier = modifier.padding(top = 22.dp))
            LazyColumn {
                item {
                    Text(
                        modifier = modifier.padding(start = 16.dp, end = 16.dp),
                        text = registration.description.trim(),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}