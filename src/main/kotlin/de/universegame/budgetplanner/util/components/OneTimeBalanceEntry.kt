package de.universegame.budgetplanner.util.components

import java.time.LocalDate

data class OneTimeBalanceEntry(
    override var amount: Double,
    override var usage: String,
    override var containerId: Int,
    var date: LocalDate = LocalDate.now()
):IBalanceEntry
