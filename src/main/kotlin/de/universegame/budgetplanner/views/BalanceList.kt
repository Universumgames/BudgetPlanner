package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.components.globalBalanceContainer
import de.universegame.budgetplanner.util.composable.BalanceEntryRow
import de.universegame.budgetplanner.util.composable.MonthOverviewRow
import de.universegame.budgetplanner.util.composable.ScrollColumn
import de.universegame.budgetplanner.util.composable.YearOverviewRow

@Composable
fun BalanceListView() {
    ScrollColumn(modifier = Modifier.fillMaxSize()) {
        for (entry in globalBalanceContainer.sortedEntries()) {
            val year = entry.key
            val yearEntry = entry.value
            YearOverviewRow(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),entry = yearEntry, timedList = globalBalanceContainer.regularBalanceEntries, currency = "€") {
                println(
                    yearEntry.year.value
                )
            }

            for (month in yearEntry.months) {
                val total = month.total(globalBalanceContainer.regularBalanceEntries)
                if(total != 0.0) {
                    MonthOverviewRow(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp), entry = month, timedList = globalBalanceContainer.regularBalanceEntries, currency = "€"){
                        println(
                            month.month.monthValue
                        )
                    }
                    for (monthEntry in month.entries) {
                        BalanceEntryRow(entry = monthEntry, currency = "€", onClick =  { entry ->
                            println(entry.amount)
                        })
                    }
                }
            }
        }
    }
}
/*for (i in 0..50) {
    BalanceEntry(entry=OneTimeBalanceEntry(i, "test", 0), onClick = {entry->
        println(entry.amount)
    })
}*/