package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.YearMonth

@Serializable
data class MonthlyEntries(
    var entries : MutableList<OneTimeBalanceEntry> = mutableListOf(),
    var month: YearMonth
)
