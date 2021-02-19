package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable

/**
 * Basic balance entry element
 * */
interface IBalanceEntry {
    var amount: Double
    var usage: String
    var containerId: Int
    val type: EntryType
    val name: String

    /**
     * if element is not serializable, Return a similar, serializable object
     * */
    fun toSerializable():IBalanceEntry
    /**
     * if element is only for serialization purposes, return the usable coresponding data struct
     * */
    fun toUsable():IBalanceEntry
}

@Serializable
enum class EntryType {
    ONE_TIME,
    RECURRING
}