package com.darleyleal.financebuddy.domain.usercases.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal fun formatMonthAndYear(month: String, year: String): String {
    var date = ""
    when (month) {
        "01" -> date = "Jan, ${year.substring(year.length - 2)}"
        "02" -> date = "Feb, ${year.substring(year.length - 2)}"
        "03" -> date = "Mar, ${year.substring(year.length - 2)}"
        "04" -> date = "Apr, ${year.substring(year.length - 2)}"
        "05" -> date = "May, ${year.substring(year.length - 2)}"
        "06" -> date = "Jun, ${year.substring(year.length - 2)}"
        "07" -> date = "Jul, ${year.substring(year.length - 2)}"
        "08" -> date = "Aug, ${year.substring(year.length - 2)}"
        "09" -> date = "Sep, ${year.substring(year.length - 2)}"
        "10" -> date = "Oct, ${year.substring(year.length - 2)}"
        "11" -> date = "Nov, ${year.substring(year.length - 2)}"
        "12" -> date = "Dec, ${year.substring(year.length - 2)}"
    }
    return date
}