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
                tmp.add(MonthlyEntries(yearMonth = year.atMonth(i)))
            }
            this.months = tmp.toList()
        }
        this.months = this.months.sortedBy { it.yearMonth.monthValue }
    }

    @Deprecated("use getChange() to get total change")
    val simpleChange: Double
        get() {
            var totalV: Double = 0.0
            months.forEach {
                totalV += it.simpleChange
            }
            return totalV
        }

    fun getChange(timedList: List<RecurringBalanceEntry>): Double {
        var total = 0.0
        months.forEach { month ->
            total += month.getChange(timedList)
        }
        return total
    }

    fun addOneTimeEntry(entry: OneTimeBalanceEntry): Boolean{
        if(entry.date.year != year.value) return false
        months[entry.date.monthValue - 1].addOneTimeEntry(entry)
        return true
    }

    fun deleteOneTimeEntry(entry: OneTimeBalanceEntry): Boolean{
        if(entry.date.year != year.value) return false
        return months[entry.date.monthValue - 1].deleteOneTimeEntry(entry)
    }
}
