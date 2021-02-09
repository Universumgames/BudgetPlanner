package de.universegame.budgetplanner.util.components

import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

data class MonthlyEntries(
    var entries: MutableList<OneTimeBalanceEntry> = mutableListOf(),
    var month: YearMonth
) {
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
        for (regular in container.regularBalanceEntries) {
            val now = month.atDay(LocalDate.now().dayOfMonth)
            val yearsDiff = regular.startTime.year - now.year
            val monthsDiff = regular.startTime.monthValue - now.monthValue
            val dayDiff = regular.startTime.dayOfMonth - now.dayOfMonth
            val weeksDiff: Int = (dayDiff - (dayDiff % 7)) / 7
            if (regular.startTime <= now && regular.endTime >= now) {
                when (regular.interval) {
                    Interval.DAILY -> {
                        for (i in 1..31) {
                            if (month.isValidDay(i)) {
                                clonedEntries.add(
                                    OneTimeBalanceEntry(
                                        regular.amount,
                                        regular.usage,
                                        regular.containerId,
                                        LocalDate.of(month.year, month.monthValue, i),
                                        EntryType.REGULAR
                                    )
                                )
                            }
                        }
                    }
                    Interval.WEEKLY -> {
                        //ToDo
                    }
                    Interval.TWICE_MONTHLY -> {
                        //ToDo
                    }
                    Interval.MONTHLY -> {
                        clonedEntries.add(
                            OneTimeBalanceEntry(
                                regular.amount,
                                regular.usage,
                                regular.containerId,
                                LocalDate.of(month.year, month.monthValue, regular.startTime.dayOfMonth),
                                EntryType.REGULAR
                            )
                        )
                    }
                    Interval.QUARTERLY -> {
                        if (monthsDiff % 3 == 0) {
                            clonedEntries.add(
                                OneTimeBalanceEntry(
                                    regular.amount,
                                    regular.usage,
                                    regular.containerId,
                                    LocalDate.of(month.year, month.monthValue, regular.startTime.dayOfMonth),
                                    EntryType.REGULAR
                                )
                            )
                        }
                    }
                    Interval.BIANNUAL -> {
                        if (monthsDiff % 6 == 0) {
                            clonedEntries.add(
                                OneTimeBalanceEntry(
                                    regular.amount,
                                    regular.usage,
                                    regular.containerId,
                                    LocalDate.of(month.year, month.monthValue, regular.startTime.dayOfMonth),
                                    EntryType.REGULAR
                                )
                            )
                        }
                    }
                    Interval.ANNUAL -> {
                        if (yearsDiff == 1 && monthsDiff == 0) {
                            clonedEntries.add(
                                OneTimeBalanceEntry(
                                    regular.amount,
                                    regular.usage,
                                    regular.containerId,
                                    LocalDate.of(
                                        month.year,
                                        regular.startTime.monthValue,
                                        regular.startTime.dayOfMonth
                                    ),
                                    EntryType.REGULAR
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

    fun total(timedList: List<RegularBalanceEntry>): Double {
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


