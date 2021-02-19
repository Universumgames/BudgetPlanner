package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import kotlin.random.Random

@Serializable
/**
 * Data struct for storing a single location for money
 * (like "cash", "account", "wallet")
 * */
data class WalletData(
    val name: String,
    val id: Int
)

@Serializable
/**
 * Data Storage for all expenses and income, only for serialization purposes
 * */
data class IBalanceContainer(
    var walletNames: MutableList<WalletData> = mutableListOf(WalletData("Wallet", 0)),
    var recurringBalanceEntries: MutableList<IRecurringBalanceEntry> = mutableListOf(),
    var entries: MutableMap<Int, IYearlyEntries> = mutableMapOf()
) {

    fun toUsable(): BalanceContainer {
        val usableRecurringBalanceEntries: MutableList<RecurringBalanceEntry> = mutableListOf()
        val usableEntries: MutableMap<Year, YearlyEntries> = mutableMapOf()
        for (recurringEntry in recurringBalanceEntries) {
            usableRecurringBalanceEntries.add(recurringEntry.toUsable())
        }
        for (entry in entries) {
            usableEntries[Year.of(entry.key)] = entry.value.toUsable()
        }
        val container = BalanceContainer()
        container.walletNames = walletNames
        container.recurringBalanceEntries = usableRecurringBalanceEntries
        container.entries = usableEntries
        return container
    }
}

/**
 * Data Storage for all expenses and income, not serializable
 * */
class BalanceContainer {
    var walletNames: MutableList<WalletData> = mutableListOf(WalletData("Wallet", 0))
    var recurringBalanceEntries: MutableList<RecurringBalanceEntry> = mutableListOf()
    var entries: MutableMap<Year, YearlyEntries> = mutableMapOf()

    fun toSerializable(): IBalanceContainer {
        val serializableRecurringBalanceEntries: MutableList<IRecurringBalanceEntry> = mutableListOf()
        val serializableEntries: MutableMap<Int, IYearlyEntries> = mutableMapOf()
        for (recurringEntry in recurringBalanceEntries) {
            serializableRecurringBalanceEntries.add(recurringEntry.toSerializable())
        }
        for (entry in entries) {
            serializableEntries[entry.key.value] = entry.value.toSerializable()
        }
        return IBalanceContainer(walletNames, serializableRecurringBalanceEntries, serializableEntries)
    }

    fun sortedEntries(): MutableMap<Year, YearlyEntries> {
        val last = entries.keys.last()
        var until : Year = entries.keys.last()
        for(rec in recurringBalanceEntries){
            if(rec.endTime.year > until.value)
                until = Year.of(rec.endTime.year)
        }
        for(i in last.value..until.value){
            val year = Year.of(i)
            if(!entries.keys.contains(year)){
                entries[year] = YearlyEntries(year)
            }
        }
        return entries.toSortedMap()
    }


    fun totalTil(year: Year): Double {
        return totalTil(YearMonth.of(year.value, 12))
    }

    fun totalTil(yearMonth: YearMonth): Double {
        var total = 0.0
        val sorted = sortedEntries()
        for (entry in sorted) {
            if (entry.key.value > yearMonth.year) break
            for (monthEntry in entry.value.months) {
                if (monthEntry.yearMonth.isAfter(yearMonth)) break
                val change = monthEntry.getChange(recurringBalanceEntries)
                total += change
            }
        }
        return total
    }

    fun addOneTimeEntry(
        date: LocalDate,
        amount: Double,
        containerId: Int = 0,
        usage: String = "Custom Entry",
        name: String = "No name declared"
    ) {
        addOneTimeEntry(
            OneTimeBalanceEntry(
                amount,
                date = date,
                containerId = containerId,
                usage = usage,
                name = name
            )
        )
    }

    fun addOneTimeEntry(entry: OneTimeBalanceEntry) {
        val year = Year.of(entry.date.year)
        var yearEntries: YearlyEntries = YearlyEntries(year)
        if (entries.containsKey(year)) {
            yearEntries = entries[year]!!
        } else
            entries[year] = yearEntries
        yearEntries.addOneTimeEntry(entry)
    }

    fun addOneTimeEntryList(entries: List<OneTimeBalanceEntry>) {
        entries.forEach { addOneTimeEntry(it) }
    }

    fun addRecurringEntry(entry: RecurringBalanceEntry) {
        recurringBalanceEntries.add(entry)
    }

    fun addWallet(name: String): Boolean {
        if (name.isEmpty()) return false
        if (walletNames.any { it.name == name }) return true
        walletNames.add(WalletData(name, walletNames.size))
        return true
    }

    fun deleteOneTimeEntry(entry: OneTimeBalanceEntry): Boolean {
        val year = Year.of(entry.date.year)
        var yearEntries: YearlyEntries = YearlyEntries(year)
        if (entries.containsKey(year)) {
            yearEntries = entries[year]!!
        } else
            entries[year] = yearEntries
        return yearEntries.deleteOneTimeEntry(entry)
    }

    fun getWalletDataById(id: Int): WalletData {
        return walletNames.find { it.id == id } ?: WalletData("Deleted / Not found", -1)
    }

    fun setRecurringEntryHandled(entry: OneTimeBalanceEntry): Boolean {
        if (entry.type != EntryType.RECURRING) return false
        val recEntry =
            recurringBalanceEntries.find { it.amount == entry.amount && it.usage == entry.usage && it.containerId == entry.containerId }
                ?: return false
        val rec = recEntry.copy()
        recurringBalanceEntries.remove(recEntry)
        rec.startTime = when (rec.interval) {
            Interval.DAILY -> entry.date.plusDays(1L)
            Interval.WEEKLY -> entry.date.plusDays(7L)
            Interval.TWICE_MONTHLY -> entry.date.plusDays(14L)
            Interval.MONTHLY -> entry.date.plusMonths(1L)
            Interval.QUARTERLY -> entry.date.plusMonths(3L)
            Interval.BIANNUAL -> entry.date.plusMonths(6L)
            Interval.ANNUAL -> entry.date.plusYears(1L)
        }
        if (!rec.startTime.isAfter(rec.endTime))
            recurringBalanceEntries.add(rec)
        return true
    }
}

fun exampleContainer(): BalanceContainer {
    val container = BalanceContainer()
    for (i in 0..100) {
        val year = Year.of(Random.nextInt(2021, 2022))
        var t = YearlyEntries(year)
        if (container.entries.containsKey(year))
            t = container.entries[year]!!
        val month = Random.nextInt(0, 11)
        var day = Random.nextInt(1, 31)
        while (!year.atMonth(month + 1).isValidDay(day))
            day = Random.nextInt(1, 31)
        t.months[month].entries.add(
            OneTimeBalanceEntry(
                Random.nextDouble(-500.0, 500.0),
                "test",
                0,
                date = LocalDate.of(year.value, month + 1, day),
                name = "name"
            )
        )
        container.entries[year] = t
    }
    for (i in 0..10) {
        val interval = Interval.values()[Random.nextInt(1, Interval.values().size - 1)]
        container.recurringBalanceEntries.add(
            RecurringBalanceEntry(
                Random.nextDouble(-500.0, 500.0), "recurringEntry${interval.prettyName}",
                0,
                "random name",
                LocalDate.now().plusDays(Random.nextLong(-31, 31)),
                interval = interval
            )
        )
    }
    return container
}

fun loadBalanceContainer(filename: String, json: Json): BalanceContainer {
    val file = File(filename).also { file ->
        file.parentFile.mkdirs()
    }
    if (!file.exists()) {
        saveBalanceContainer(BalanceContainer(), filename, json)
    }
    val jsonData = file.readText()
    val interfaceData = json.decodeFromString<IBalanceContainer>(jsonData)
    return interfaceData.toUsable()
}

fun saveBalanceContainer(container: BalanceContainer, filename: String, json: Json) {
    val file = File(filename).also { file ->
        file.parentFile.mkdirs()
    }
    file.createNewFile()
    val data: String = json.encodeToString(container.toSerializable())
    file.writeText(data)
}
