package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.components.BalanceContainer
import de.universegame.budgetplanner.util.components.MonthlyEntries
import de.universegame.budgetplanner.util.composable.ScrollColumn
import java.time.LocalDate

@Composable
/**
 * Contains the YearOverviews
 * The SummaryList for the total amount of change in income
 * */
fun BalanceListView(balanceContainer: BalanceContainer, onMonthSelected: (MonthlyEntries) -> Unit) {
    ScrollColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        for (entry in balanceContainer.sortedEntries()) {
            val year = entry.key
            val yearEntry = entry.value
            val dropdown = remember { mutableStateOf(year.value == LocalDate.now().year) }
            YearOverviewRow(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                entry = yearEntry,
                timedList = balanceContainer.recurringBalanceEntries,
                currency = "€",
                container = balanceContainer
            ) {
                dropdown.value = !dropdown.value
            }

            if (dropdown.value)
                for (month in yearEntry.months) {
                    val total = month.getChange(balanceContainer.recurringBalanceEntries)
                    if (total != 0.0) {
                        MonthOverviewRow(
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                            entry = month,
                            timedList = balanceContainer.recurringBalanceEntries,
                            currency = "€",
                            container = balanceContainer
                        ) {
                            onMonthSelected(month)
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