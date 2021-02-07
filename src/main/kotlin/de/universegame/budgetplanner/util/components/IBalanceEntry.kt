package de.universegame.budgetplanner.util.components

import kotlinx.serialization.Serializable

@Serializable
interface IBalanceEntry {
    var amount: Number
    var usage: String
    var containerId: Int
}