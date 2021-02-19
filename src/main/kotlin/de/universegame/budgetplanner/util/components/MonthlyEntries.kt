package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Serializable
data class IMonthlyEntries(
    var entries: MutableList<IOneTimeBalanceEntry>,
    var yearMonth: String
) {

    fun toUsable(): MonthlyEntries {
        val usableEntries: MutableList<OneTimeBalanceEntry> = mutableListOf()
        for (entry in entries) {
            usableEntries.add(entry.toUsable())
        }
        return MonthlyEntries(usableEntries, YearMonth.from(DateTimeFormatter.ofPattern("MM-yyyy").parse(yearMonth)))
    }
}

data class MonthlyEntries(
    var entries: MutableList<OneTimeBalanceEntry> = mutableListOf(),
    var yearMonth: YearMonth
) {

    fun toSerializable(): IMonthlyEntries {
        val serializableEntries = mutableListOf<IOneTimeBalanceEntry>()
        for (entry in entries) {
            serializableEntries.add(entry.toSerializable())
        }
        return IMonthlyEntries(serializableEntries, yearMonth.format(DateTimeFormatter.ofPattern("MM-yyyy")))
    }

    val simpleChange: Double
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
            val yearsDiff = recurring.startTime.year - yearMonth.year
            val monthsDiff = recurring.startTime.monthValue - yearMonth.monthValue
            if (YearMonth.from(recurring.startTime) <= yearMonth && YearMonth.from(recurring.endTime) >= yearMonth) {
                when (recurring.interval) {
                    Interval.DAILY -> {
                        for (i in 1..31) {
                            if (yearMonth.isValidDay(i)) {
                                clonedEntries.add(
                                    OneTimeBalanceEntry(
                                        recurring.amount,
                                        recurring.usage,
                                        recurring.containerId,
                                        recurring.name,
                                        LocalDate.of(yearMonth.year, yearMonth.monthValue, i),
                                        EntryType.RECURRING
                                    )
                                )
                            }
                        }
                    }
                    Interval.WEEKLY -> {
                        for (i in 1..31) {
                            if (yearMonth.isValidDay(i)) {
                                val now = yearMonth.atDay(i)
                                val dayDiff = recurring.startTime.dayOfMonth - i
                                if (dayDiff % 7 == 0) {
                                    clonedEntries.add(
                                        OneTimeBalanceEntry(
                                            recurring.amount,
                                            recurring.usage,
                                            recurring.containerId,
                                            recurring.name,
                                            LocalDate.of(yearMonth.year, yearMonth.monthValue, now.dayOfMonth),
                                            EntryType.RECURRING
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Interval.TWICE_MONTHLY -> {
                        for (i in 1..31) {
                            if (yearMonth.isValidDay(i)) {
                                val now = yearMonth.atDay(i)
                                val dayDiff = recurring.startTime.dayOfMonth - i
                                if (dayDiff % 7 == 0 && dayDiff % 14 == 0) {
                                    clonedEntries.add(
                                        OneTimeBalanceEntry(
                                            recurring.amount,
                                            recurring.usage,
                                            recurring.containerId,
                                            recurring.name,
                                            LocalDate.of(yearMonth.year, yearMonth.monthValue, now.dayOfMonth),
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
                                LocalDate.of(yearMonth.year, yearMonth.monthValue, recurring.startTime.dayOfMonth),
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
                                    LocalDate.of(yearMonth.year, yearMonth.monthValue, recurring.startTime.dayOfMonth),
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
                                    LocalDate.of(yearMonth.year, yearMonth.monthValue, recurring.startTime.dayOfMonth),
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
                                        yearMonth.year,
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

    fun getChange(timedList: List<RecurringBalanceEntry>): Double {
        var change = simpleChange
        timedList.forEach { entry ->
            if (entry.startTime.year <= yearMonth.year && entry.startTime.month <= yearMonth.month) {
                if (entry.endTime.year >= yearMonth.year && entry.endTime.month >= yearMonth.month) {
                    val value: Double
                    val amount = entry.amount
                    val monthsPassed =
                        (entry.startTime.year - yearMonth.year) * 12 + (12 - abs(entry.startTime.monthValue - yearMonth.monthValue))
                    var weeksToCalc = 0
                    for (i in 1..31) {
                        if (yearMonth.isValidDay(i)) {
                            val dayDiff = entry.startTime.dayOfMonth - i
                            if (dayDiff % 7 == 0) {
                                weeksToCalc++
                            }
                        }
                    }
                    value = when (entry.interval) {
                        Interval.DAILY -> amount * yearMonth.atEndOfMonth().dayOfMonth
                        Interval.WEEKLY -> amount * weeksToCalc
                        Interval.TWICE_MONTHLY -> amount * 2
                        Interval.MONTHLY -> amount
                        Interval.QUARTERLY -> amount * (if (monthsPassed % 3 == 0) 1 else 0)
                        Interval.BIANNUAL -> amount * (if (monthsPassed % 6 == 0) 1 else 0)
                        Interval.ANNUAL -> amount * (if (monthsPassed % 12 == 0) 1 else 0)
                    }
                    change += value
                }
            }
        }
        return change
    }

    fun addOneTimeEntry(entry: OneTimeBalanceEntry): Boolean {
        if (entry.date.monthValue != yearMonth.monthValue) return false
        if (!entries.contains(entry))
            entries.add(entry)
        return true
    }

    fun deleteOneTimeEntry(entry: OneTimeBalanceEntry): Boolean {
        if (entry.date.monthValue != yearMonth.monthValue) return false
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


