package de.universegame.budgetplanner.util

enum class SubWindowType{
    NONE,
    ADD_ONETIME_ENTRY,
    ADD_REGULAR_ENTRY
}

enum class AddEntryType(index: Int, name: String){
    OneTime(0,"One Time"),
    Regular(1, "Timed")
}