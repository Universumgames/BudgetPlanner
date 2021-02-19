package de.universegame.budgetplanner.util

import java.time.YearMonth
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Helper function to convert a month into it's english name
 * (1 -> "January", 2 -> "February", ...)
 * */
fun YearMonth.toSimpleFullName(): String {
    return when (this.monthValue) {
        1 -> "January " + this.year
        2 -> "February " + this.year
        3 -> "March " + this.year
        4 -> "April " + this.year
        5 -> "May " + this.year
        6 -> "June " + this.year
        7 -> "July " + this.year
        8 -> "August " + this.year
        9 -> "September " + this.year
        10 -> "October " + this.year
        11 -> "November " + this.year
        12 -> "December " + this.year
        else -> "Error"
    }
}

/**
 * Helper function to round a double to *decimal* places
 * (0.02356.round(2) -> 0.02)
 * */
fun Double.round(decimal: Int): Double{
    return (this * 10.0.pow(decimal)).roundToInt().toDouble() / 10.0.pow(decimal)
}

/**
 * Helper function to convert double into a neat string for displaying a currency
 * @param conditionalPlus if true an additional "+" will be in front of double, if value is positive (10 -> "+10", -10 -> "-10")
 * @param currency the currency string, that should be appended, like "€" or "$"
 * */
fun Double.toCurrencyString(currency: String, conditionalPlus: Boolean = true): String{
    return (if(this >= 0 && conditionalPlus) "+" else "") + this.round(2).toString() + currency
}

/**
 * Helper function to shorten a given string
 * @param maxLength Maximum length for string
 * @return String with a length of maxLength, if string is longer, the last three characters will be "..."
 * */
fun String.short(maxLength: Int): String{
    return if(this.length > maxLength)
        this.substring(0, maxLength-3) + "..."
    else this
}