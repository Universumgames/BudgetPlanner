package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.Year

@Serializable
data class YearlyEntries(
    val year: Year
){
    var months: List<MonthlyEntries> = listOf()
        private set

    init{
        var tmp = mutableListOf<MonthlyEntries>()
        for(i in 1..12){
            tmp.add(MonthlyEntries(month = year.atMonth(i)))
        }
        this.months = tmp.toList()
    }
}
