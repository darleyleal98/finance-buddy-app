package com.darleyleal.financebuddy.domain.usercases.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun convertDate(inputDate: String?): String {
    val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)

    val date = inputDate?.let { inputFormat.parse(inputDate) }
    return date?.let { outputFormat.format(it) }.toString()
}