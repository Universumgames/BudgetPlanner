package de.universegame.budgetplanner.util.components

import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

data class MonthlyEntries(
    var entries: MutableList<OneTimeBalanceEntry> = mutableListOf(),
    var month: YearMonth
) {
    val oneTimeTotal: Double
        get() {
            var totalV: Double = 0.0
            entries.forEach {
                totalV += it.amount as Double
            }
            return totalV
        }

    fun total(timedList: List<RegularBalanceEntry>): Double {
        var total = oneTimeTotal
        timedList.forEach { entry ->
            if (entry.startTime.year <= month.year && entry.startTime.month <= month.month) {
                if (entry.endTime.year >= month.year && entry.endTime.month >= month.month) {
                    var value = 0.0
                    val amount = entry.amount as Double
                    val monthsPassed =
                        (entry.startTime.year - month.year) * 12 + (entry.startTime.monthValue - month.monthValue)
                    when (entry.interval) {
                        Interval.DAILY -> value = amount * LocalDate.now().dayOfMonth
                        Interval.WEEKLY -> value = amount * monthsPassed * 4
                        Interval.TWICE_MONTHLY -> value = amount * monthsPassed * 2
                        Interval.MONTHLY -> value = amount * monthsPassed
                        Interval.QUARTERLY -> value = amount * (if(monthsPassed % 3 == 0) ((monthsPassed-(monthsPassed % 3)) / 3) else 0)
                        Interval.BIANNUAL -> value = amount * (if(monthsPassed % 6 == 0) ((monthsPassed-(monthsPassed % 6)) / 6) else 0)
                        Interval.ANNUAL -> value = amount * (if(monthsPassed % 12 == 0) ((monthsPassed-(monthsPassed % 12)) / 12) else 0)
                    }
                    total += value
                }
            }
        }
        return total
    }
}

fun currentMonth(container: BalanceContainer): MonthlyEntries {
    return if(container.entries.containsKey(Year.now())){
        container.entries[Year.now()]!!.months[YearMonth.now().monthValue-1]
    }else {
        val newYear = YearlyEntries(Year.now())
        container.entries[Year.now()] = newYear
        newYear.months[YearMonth.now().monthValue-1]
    }
}


