package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OneTimeBalanceEntry(
    override var amount: Number,
    override var usage: String,
    override var containerId: Int,
    var time: LocalDateTime = LocalDateTime.now()
):IBalanceEntry
