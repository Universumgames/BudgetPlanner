package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class OneTimeBalanceEntry(
    override var amount: Double,
    override var usage: String,
    override var containerId: Int,
    override var name: String,
    var date: LocalDate = LocalDate.now(),
    val displayTypeOverride: EntryType = EntryType.ONE_TIME
) : IBalanceEntry {
    override val type: EntryType
        get() = displayTypeOverride

    override fun toSerializable(): IOneTimeBalanceEntry {
        return IOneTimeBalanceEntry(
            amount,
            usage,
            containerId,
            name,
            date.format(DateTimeFormatter.ISO_LOCAL_DATE),
            displayTypeOverride
        )
    }

    override fun toUsable(): OneTimeBalanceEntry {
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is OneTimeBalanceEntry) return false
        return other.date == date && other.displayTypeOverride == displayTypeOverride &&
                other.amount == amount && other.name == name && other.usage == usage && other.type == type &&
                other.containerId == containerId
    }

    override fun hashCode(): Int {
        var result = amount.hashCode()
        result = 31 * result + usage.hashCode()
        result = 31 * result + containerId
        result = 31 * result + name.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + displayTypeOverride.hashCode()
        return result
    }


}

@Serializable
data class IOneTimeBalanceEntry(
    override var amount: Double = 0.0,
    override var usage: String = "",
    override var containerId: Int = 0,
    override var name: String,
    val dateString: String = LocalDate.now().toString(),
    override val type: EntryType = EntryType.ONE_TIME
) : IBalanceEntry {

    override fun toSerializable(): IOneTimeBalanceEntry {
        return this
    }

    override fun toUsable(): OneTimeBalanceEntry {
        return OneTimeBalanceEntry(
            amount,
            usage,
            containerId,
            name,
            LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(dateString))
        )
    }
}
