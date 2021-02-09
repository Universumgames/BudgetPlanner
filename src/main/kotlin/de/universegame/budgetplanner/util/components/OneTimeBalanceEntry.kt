package de.universegame.budgetplanner.util.components

import java.time.LocalDate

data class OneTimeBalanceEntry(
    override var amount: Double,
    override var usage: String,
    override var containerId: Int,
    var date: LocalDate = LocalDate.now(),
    val displayTypeOverride: EntryType = EntryType.ONE_TIME
):IBalanceEntry{
    override val type: EntryType
        get() = displayTypeOverride
}
