package com.darleyleal.financebuddy.domain.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun convertToCurrency(number: Float): String {
    val symbols = DecimalFormatSymbols().apply {
        decimalSeparator = ','
        groupingSeparator = '.'
    }

    val df = DecimalFormat("#,##0.00", symbols)
    val formattedNumber = df.format(number)

    return "\$$formattedNumber"
}