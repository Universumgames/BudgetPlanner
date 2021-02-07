package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.Year

@Serializable
data class BalanceLocationName(
    val name: String,
    val id: Int
)

@Serializable
class BalanceContainer{
    var locationNames: MutableList<BalanceLocationName> = mutableListOf(BalanceLocationName("Wallet", 0))
    var regularBalanceEntries : MutableList<RegularBalanceEntry> = mutableListOf()
    var entries: Map<Year, YearlyEntries> = mapOf()
}
