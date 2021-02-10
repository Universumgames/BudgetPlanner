package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.Year

@Serializable
data class IYearlyEntries(
    val year: String = Year.now().value.toString(),
    var months: List<IMonthlyEntries> = listOf()
){
    fun toUsable():YearlyEntries{
        val usableMonths: MutableList<MonthlyEntries> = mutableListOf()
        for(entry in months){
            usableMonths.add(entry.toUsable())
        }
        return YearlyEntries(Year.of(year.toInt()), usableMonths)
    }
}

data class YearlyEntries(
    val year: Year,
    var months: List<MonthlyEntries> = listOf()
) {

    fun toSerializable():IYearlyEntries{
        val serializableMonths: MutableList<IMonthlyEntries> = mutableListOf()
        for(entry in months){
            serializableMonths.add(entry.toSerializable())
        }
        return IYearlyEntries(year.value.toString(), serializableMonths)
    }

    init {
        if(this.months.isEmpty()) {
            val tmp = mutableListOf<MonthlyEntries>()
            for (i in 1..12) {
                tmp.add(MonthlyEntries(month = year.atMonth(i)))
            }
            this.months = tmp.toList()
        }
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
