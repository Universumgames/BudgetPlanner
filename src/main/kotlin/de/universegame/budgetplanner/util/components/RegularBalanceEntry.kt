package de.universegame.budgetplanner.util.components

import java.time.LocalDate

enum class Interval{
    DAILY,
    WEEKLY,
    TWICE_MONTHLY,
    MONTHLY,
    QUARTERLY,
    BIANNUAL,
    ANNUAL
}

data class RegularBalanceEntry(
    override var amount: Double,
    override var usage: String,
    override var containerId: Int = 0,
    var startTime: LocalDate = LocalDate.now(),
    var endTime: LocalDate = LocalDate.now().plusYears(1),
    var interval: Interval = Interval.MONTHLY
):IBalanceEntry
