package de.universegame.budgetplanner.util.components

import java.time.LocalDateTime

data class OneTimeBalanceEntry(
    override var amount: Double,
    override var usage: String,
    override var containerId: Int,
    var time: LocalDateTime = LocalDateTime.now()
):IBalanceEntry
