package de.universegame.budgetplanner.util.components

import java.time.Year

data class BalanceLocationName(
    val name: String,
    val id: Int
)

class BalanceContainer{
    var locationNames: MutableList<BalanceLocationName> = mutableListOf(BalanceLocationName("Wallet", 0))
    var regularBalanceEntries : MutableList<RegularBalanceEntry> = mutableListOf()
    var entries: MutableMap<Year, YearlyEntries> = mutableMapOf()

    fun sortedEntries(): MutableMap<Year, YearlyEntries>{
        return entries.toSortedMap()
    }
}

val globalBalanceContainer = BalanceContainer()
