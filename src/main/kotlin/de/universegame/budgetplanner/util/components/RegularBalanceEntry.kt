package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
enum class Interval(val id: Int, val prettyName: String) {
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
) : IBalanceEntry {
    override val type: EntryType
        get() = EntryType.REGULAR

    override fun toSerializable(): IRegularBalanceEntry {
        return IRegularBalanceEntry(
            amount,
            usage,
            containerId,
            type,
            interval,
            startTime.format(DateTimeFormatter.ISO_LOCAL_DATE),
            endTime.format(
                DateTimeFormatter.ISO_LOCAL_DATE
            )
        )
    }

    override fun toUsable(): RegularBalanceEntry {
        return this
    }
}

@Serializable
data class IRegularBalanceEntry(
    override var amount: Double = 0.0,
    override var usage: String = "",
    override var containerId: Int = 0,
    override val type: EntryType = EntryType.REGULAR,
    var interval: Interval = Interval.MONTHLY,
    val startDate: String = LocalDate.now().toString(),
    val endDate: String = LocalDate.now().toString()
) : IBalanceEntry {

    override fun toSerializable(): IRegularBalanceEntry {
        return this
    }

    override fun toUsable(): RegularBalanceEntry {
        return RegularBalanceEntry(
            amount,
            usage,
            containerId,
            LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(startDate)),
            LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(endDate)),
            interval
        )
    }
}
