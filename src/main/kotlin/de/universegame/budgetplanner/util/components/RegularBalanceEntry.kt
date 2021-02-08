package de.universegame.budgetplanner.util.components

import java.time.LocalDate

enum class Interval(val id: Int, val prettyName: String){
    DAILY(0, "Daily"),
    WEEKLY(1, "Weekly"),
    TWICE_MONTHLY(2, "Twice a month"),
    MONTHLY(3, "Monthly"),
    QUARTERLY(4, "Quarterly"),
    BIANNUAL(5, "Biannual"),
    ANNUAL(6, "Yearly")
}

data class RegularBalanceEntry(
    override var amount: Double,
    override var usage: String,
    override var containerId: Int = 0,
    var startTime: LocalDate = LocalDate.now(),
    var endTime: LocalDate = LocalDate.now().plusYears(1),
    var interval: Interval = Interval.MONTHLY
):IBalanceEntry
