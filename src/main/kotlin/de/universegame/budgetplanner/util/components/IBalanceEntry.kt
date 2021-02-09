package de.universegame.budgetplanner.util.components

interface IBalanceEntry {
    var amount: Double
    var usage: String
    var containerId: Int
    val type: EntryType
}

enum class EntryType{
    ONE_TIME,
    REGULAR
}