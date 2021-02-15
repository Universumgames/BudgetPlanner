package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable


interface IBalanceEntry {
    var amount: Double
    var usage: String
    var containerId: Int
    val type: EntryType
    val name: String

    fun toSerializable():IBalanceEntry
    fun toUsable():IBalanceEntry
}

@Serializable
enum class EntryType {
    ONE_TIME,
    RECURRING
}