package de.universegame.budgetplanner.util.components

import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import kotlin.random.Random

data class BalanceLocationName(
    val name: String,
    val id: Int
)

class BalanceContainer {
    var locationNames: MutableList<BalanceLocationName> = mutableListOf(BalanceLocationName("Wallet", 0))
    var regularBalanceEntries: MutableList<RegularBalanceEntry> = mutableListOf()
    var entries: MutableMap<Year, YearlyEntries> = mutableMapOf()

    fun sortedEntries(): MutableMap<Year, YearlyEntries> {
        return entries.toSortedMap()
    }

    fun totalTil(year: Year): Double {
        var total = 0.0
        val sorted = sortedEntries()
        for (entry in sorted) {
            if (entry.key > year) break
            total += entry.value.total(regularBalanceEntries)
        }
        return total
    }

    fun totalTil(month: YearMonth): Double {
        var total = 0.0
        val sorted = sortedEntries()
        for (entry in sorted) {
            if (entry.key.value > month.year) break
            for (monthEntry in entry.value.months) {
                if (monthEntry.month.monthValue > month.monthValue) break
                total += monthEntry.total(regularBalanceEntries)
            }
        }
        return total
    }

    fun addOneTimeEntry(date: LocalDate, amount: Double, containerId: Int = 0, usage: String = "undefined") {
        val year = Year.of(date.year)
        var yearEntries: YearlyEntries = YearlyEntries(year)
        if (entries.containsKey(year)) {
            yearEntries = entries[year]!!
        } else
            entries[year] = yearEntries
        yearEntries.months[date.monthValue - 1].entries.add(
            OneTimeBalanceEntry(
                amount,
                date = date,
                containerId = containerId,
                usage = usage
            )
        )
    }
}

fun loadBalanceContainer(filename: String): BalanceContainer {
    val container = BalanceContainer()
    for( i in 0..100){
        val year = Year.of(Random.nextInt(1990, 2025))
        var t = YearlyEntries(year)
        if(container.entries.containsKey(year))
            t = container.entries[year]!!
        t.months[Random.nextInt(0, 11)].entries.add(OneTimeBalanceEntry(Random.nextDouble(-500.0, 500.0), "test", 0))
        container.entries[year] = t
    }
    //ToDo real loading
    return container
}

fun saveBalanceContainer(filename: String) {
    //ToDo saving
}
