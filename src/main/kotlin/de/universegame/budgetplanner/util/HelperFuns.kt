package de.universegame.budgetplanner.util

import java.time.YearMonth
import kotlin.math.pow
import kotlin.math.roundToInt

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

fun Double.round(decimal: Int): Double{
    return (this * 10.0.pow(decimal)).roundToInt().toDouble() / 10.0.pow(decimal)
}

fun Double.toCurrencyString(currency: String, conditionalPlus: Boolean = true): String{
    return (if(this >= 0 && conditionalPlus) "+" else "") + this.round(2).toString() + currency
}