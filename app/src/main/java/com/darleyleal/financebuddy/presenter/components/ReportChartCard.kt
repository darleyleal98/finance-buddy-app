package com.darleyleal.financebuddy.presenter.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.darleyleal.financebuddy.R

@Composable
fun AnalyticsChart(
    modifier: Modifier = Modifier,
    monthsList: List<String>,
    incomesList: List<Double>,
    expensesList: List<Double>,
) {
    if (monthsList.isEmpty() || incomesList.isEmpty() || expensesList.isEmpty()) {
        Box(modifier) {
            Text(
                text = stringResource(R.string.no_data),
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
        return
    }

    val lineParameters = listOf(
        BarParameters(
            dataName = stringResource(id = R.string.income),
            data = incomesList,
            barColor = when {
                isSystemInDarkTheme() -> Color(0xFF00E5FF)
                else -> Color(0xFF03A9F4)
            }
        ),
        BarParameters(
            dataName = stringResource(id = R.string.expense),
            data = expensesList,
            barColor = when {
                isSystemInDarkTheme() -> Color(0xFFC62828)
                else -> Color(0xFFBA704F)
            }
        )
    )

    Box(
        modifier
            .height(450.dp)
            .fillMaxWidth()
            .padding(16.dp)) {
        BarChart(
            xAxisData = monthsList,
            chartParameters = lineParameters,
            animateChart = true,
            isShowGrid = true,
            yAxisRange = 10,
            descriptionStyle = TextStyle(
                color = when {
                    isSystemInDarkTheme() -> Color.LightGray
                    else -> Color.DarkGray
                },
                fontSize = 16.sp
            ),
            xAxisStyle = TextStyle(
                color = when {
                    isSystemInDarkTheme() -> Color.LightGray
                    else -> Color.DarkGray
                },
                fontSize = 16.sp
            ),
            showYAxis = true,
            yAxisStyle = TextStyle(
                color = when {
                    isSystemInDarkTheme() -> Color.LightGray
                    else -> Color.DarkGray
                },
                fontSize = 14.sp
            ),
            barCornerRadius = 100.dp,
            spaceBetweenBars = 8.dp
        )
    }
}