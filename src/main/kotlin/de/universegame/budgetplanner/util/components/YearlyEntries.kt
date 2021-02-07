package de.universegame.budgetplanner.util.components

import java.time.Year

data class YearlyEntries(
    val year: Year
) {
    var months: List<MonthlyEntries> = listOf()
        private set

    init {
        var tmp = mutableListOf<MonthlyEntries>()
        for (i in 1..12) {
            tmp.add(MonthlyEntries(month = year.atMonth(i)))
        }
        this.months = tmp.toList()
    }

    val oneTimeTotal: Double
        get() {
            var totalV: Double = 0.0
            months.forEach {
                totalV += it.oneTimeTotal
            }
            return totalV
        }

    fun total(timedList: List<RegularBalanceEntry>): Double {
        var total = 0.0
        months.forEach { month ->
            total += month.total(timedList)
        }
        return total
    }
}
