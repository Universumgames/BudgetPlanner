package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class OneTimeBalanceEntry(
    override var amount: Double,
    override var usage: String,
    override var containerId: Int,
    var date: LocalDate = LocalDate.now(),
    val displayTypeOverride: EntryType = EntryType.ONE_TIME
): IBalanceEntry {
    override val type: EntryType
        get() = displayTypeOverride

    override fun toSerializable(): IOneTimeBalanceEntry {
        return IOneTimeBalanceEntry(amount, usage, containerId, date.format(DateTimeFormatter.ISO_LOCAL_DATE), displayTypeOverride)
    }

    override fun toUsable(): OneTimeBalanceEntry {
        return this
    }


}

@Serializable
data class IOneTimeBalanceEntry(
    override var amount: Double = 0.0,
    override var usage: String = "",
    override var containerId: Int = 0,
    val dateString: String = LocalDate.now().toString(),
    override val type: EntryType = EntryType.ONE_TIME
): IBalanceEntry {

    override fun toSerializable(): IOneTimeBalanceEntry {
        return this
    }

    override fun toUsable(): OneTimeBalanceEntry {
       return OneTimeBalanceEntry(amount, usage, containerId, LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(dateString)))
    }
}
