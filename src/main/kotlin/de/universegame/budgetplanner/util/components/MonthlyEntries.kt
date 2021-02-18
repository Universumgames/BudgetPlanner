package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Serializable
data class IMonthlyEntries(
    var entries: MutableList<IOneTimeBalanceEntry> = mutableListOf(),
    var month: String = YearMonth.now().format(DateTimeFormatter.ofPattern("MM-yyyy"))
) {

    fun toUsable(): MonthlyEntries {
        val usableEntries: MutableList<OneTimeBalanceEntry> = mutableListOf()
        for (entry in entries) {
            usableEntries.add(entry.toUsable())
        }
        return MonthlyEntries(usableEntries, YearMonth.from(DateTimeFormatter.ofPattern("MM-yyyy").parse(month)))
    }
}

data class MonthlyEntries(
    var entries: MutableList<OneTimeBalanceEntry> = mutableListOf(),
    var month: YearMonth
) {

    fun toSerializable(): IMonthlyEntries {
        val serializableEntries = mutableListOf<IOneTimeBalanceEntry>()
        for (entry in entries) {
            serializableEntries.add(entry.toSerializable())
        }
        return IMonthlyEntries(serializableEntries, month.format(DateTimeFormatter.ofPattern("MM-yyyy")))
    }

    val oneTimeTotal: Double
        get() {
            var totalV: Double = 0.0
            entries.forEach {
                totalV += it.amount
            }
            return totalV
        }

    fun simplifiedList(container: BalanceContainer, sorted: Boolean = true): List<OneTimeBalanceEntry> {
        val clone = copy()
        val clonedEntries = clone.entries.toMutableList()
        for (recurring in container.recurringBalanceEntries) {
            val yearsDiff = recurring.startTime.year - month.year
            val monthsDiff = recurring.startTime.monthValue - month.monthValue
            if (YearMonth.from(recurring.startTime) <= month && YearMonth.from(recurring.endTime) >= month) {
                when (recurring.interval) {
                    Interval.DAILY -> {
                        for (i in 1..31) {
                            if (month.isValidDay(i)) {
                                clonedEntries.add(
                                    OneTimeBalanceEntry(
                                        recurring.amount,
                                        recurring.usage,
                                        recurring.containerId,
                                        recurring.name,
                                        LocalDate.of(month.year, month.monthValue, i),
                                        EntryType.RECURRING
                                    )
                                )
                            }
                        }
                    }
                    Interval.WEEKLY -> {
                        for (i in 1..31) {
                            if (month.isValidDay(i)) {
                                val now = month.atDay(i)
                                val dayDiff = recurring.startTime.dayOfMonth - i
                                if (dayDiff % 7 == 0) {
                                    clonedEntries.add(
                                        OneTimeBalanceEntry(
                                            recurring.amount,
                                            recurring.usage,
                                            recurring.containerId,
                                            recurring.name,
                                            LocalDate.of(month.year, month.monthValue, now.dayOfMonth),
                                            EntryType.RECURRING
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Interval.TWICE_MONTHLY -> {
                        for (i in 1..31) {
                            if (month.isValidDay(i)) {
                                val now = month.atDay(i)
                                val dayDiff = recurring.startTime.dayOfMonth - i
                                if (dayDiff % 7 == 0 && dayDiff % 14 == 0) {
                                    clonedEntries.add(
                                        OneTimeBalanceEntry(
                                            recurring.amount,
                                            recurring.usage,
                                            recurring.containerId,
                                            recurring.name,
                                            LocalDate.of(month.year, month.monthValue, now.dayOfMonth),
                                            EntryType.RECURRING
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Interval.MONTHLY -> {
                        clonedEntries.add(
                            OneTimeBalanceEntry(
                                recurring.amount,
                                recurring.usage,
                                recurring.containerId,
                                recurring.name,
                                LocalDate.of(month.year, month.monthValue, recurring.startTime.dayOfMonth),
                                EntryType.RECURRING
                            )
                        )
                    }
                    Interval.QUARTERLY -> {
                        if (monthsDiff % 3 == 0) {
                            clonedEntries.add(
                                OneTimeBalanceEntry(
                                    recurring.amount,
                                    recurring.usage,
                                    recurring.containerId,
                                    recurring.name,
                                    LocalDate.of(month.year, month.monthValue, recurring.startTime.dayOfMonth),
                                    EntryType.RECURRING
                                )
                            )
                        }
                    }
                    Interval.BIANNUAL -> {
                        if (monthsDiff % 6 == 0) {
                            clonedEntries.add(
                                OneTimeBalanceEntry(
                                    recurring.amount,
                                    recurring.usage,
                                    recurring.containerId,
                                    recurring.name,
                                    LocalDate.of(month.year, month.monthValue, recurring.startTime.dayOfMonth),
                                    EntryType.RECURRING
                                )
                            )
                        }
                    }
                    Interval.ANNUAL -> {
                        if (yearsDiff == 1 && monthsDiff == 0) {
                            clonedEntries.add(
                                OneTimeBalanceEntry(
                                    recurring.amount,
                                    recurring.usage,
                                    recurring.containerId,
                                    recurring.name,
                                    LocalDate.of(
                                        month.year,
                                        recurring.startTime.monthValue,
                                        recurring.startTime.dayOfMonth
                                    ),
                                    EntryType.RECURRING
                                )
                            )
                        }
                    }
                }

            }
        }
        return clonedEntries.sortedBy { it.date }.toList()
    }

    fun sorted(): List<OneTimeBalanceEntry> {
        return entries.sortedBy { it.date }
    }

    fun total(timedList: List<RecurringBalanceEntry>): Double {
        var total = oneTimeTotal
        timedList.forEach { entry ->
            if (entry.startTime.year <= month.year && entry.startTime.month <= month.month) {
                if (entry.endTime.year >= month.year && entry.endTime.month >= month.month) {
                    val value: Double
                    val amount = entry.amount
                    val monthsPassed =
                        (entry.startTime.year - month.year) * 12 + (entry.startTime.monthValue - month.monthValue)
                    value = when (entry.interval) {
                        Interval.DAILY -> amount * month.atEndOfMonth().dayOfMonth
                        Interval.WEEKLY -> amount * monthsPassed * 4
                        Interval.TWICE_MONTHLY -> amount * monthsPassed * 2
                        Interval.MONTHLY -> amount * monthsPassed
                        Interval.QUARTERLY -> amount * (if (monthsPassed % 3 == 0) ((monthsPassed - (monthsPassed % 3)) / 3) else 0)
                        Interval.BIANNUAL -> amount * (if (monthsPassed % 6 == 0) ((monthsPassed - (monthsPassed % 6)) / 6) else 0)
                        Interval.ANNUAL -> amount * (if (monthsPassed % 12 == 0) ((monthsPassed - (monthsPassed % 12)) / 12) else 0)
                    }
                    total += value
                }
            }
        }
        return total
    }

    fun addOneTimeEntry(entry: OneTimeBalanceEntry): Boolean {
        if (entry.date.monthValue != month.monthValue) return false
        if (!entries.contains(entry))
            entries.add(entry)
        return true
    }

    fun deleteOneTimeEntry(entry: OneTimeBalanceEntry): Boolean {
        if (entry.date.monthValue != month.monthValue) return false
        if (!entries.any { it.date == entry.date && it.amount == entry.amount && it.usage == entry.usage && it.containerId == entry.containerId }) return false
        entries.remove(entry)
        return true
    }
}

fun currentMonth(container: BalanceContainer): MonthlyEntries {
    return if (container.entries.containsKey(Year.now())) {
        container.entries[Year.now()]!!.months[YearMonth.now().monthValue - 1]
    } else {
        val newYear = YearlyEntries(Year.now())
        container.entries[Year.now()] = newYear
        newYear.months[YearMonth.now().monthValue - 1]
    }
}


