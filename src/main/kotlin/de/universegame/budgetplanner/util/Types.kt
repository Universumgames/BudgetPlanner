package de.universegame.budgetplanner.util

enum class SubWindowType{
    NONE,
    ADD_ONETIME_ENTRY,
    ADD_RECURRING_ENTRY
}

enum class AddEntryType(index: Int, name: String){
    OneTime(0,"One Time"),
    Recurring(1, "Timed")
}