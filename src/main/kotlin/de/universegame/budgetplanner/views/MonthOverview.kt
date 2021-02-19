package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.universegame.budgetplanner.util.BalanceListColors
import de.universegame.budgetplanner.util.components.BalanceContainer
import de.universegame.budgetplanner.util.components.IBalanceEntry
import de.universegame.budgetplanner.util.components.MonthlyEntries
import de.universegame.budgetplanner.util.components.OneTimeBalanceEntry
import de.universegame.budgetplanner.util.composable.ScrollColumn
import de.universegame.budgetplanner.util.toCurrencyString
import de.universegame.budgetplanner.util.toSimpleFullName

@Composable
fun MonthOverviewView(
    month: MonthlyEntries,
    colorScheme: BalanceListColors = BalanceListColors(),
    container: BalanceContainer,
    onEntryDelete: (IBalanceEntry) -> Unit,
    onEntryHandled: (IBalanceEntry) -> Unit
) {
    val simpleEntryList = month.simplifiedList(container)
    val selectedEntry: MutableState<OneTimeBalanceEntry?> = remember { mutableStateOf(null) }
    ScrollColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(month.yearMonth.toSimpleFullName(), fontSize = 40.sp, color = colorScheme.fontColor)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            val total = container.totalTil(month.yearMonth)
            val change = month.getChange(container.recurringBalanceEntries)
            Text(
                change.toCurrencyString("€"),
                fontSize = 30.sp,
                color = if (change < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor
            )
            Spacer(Modifier.width(10.dp))
            Text(
                total.toCurrencyString("€", false),
                fontSize = 30.sp,
                color = if (total < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor
            )
        }
        for (entry in simpleEntryList) {
            BalanceEntryRow(
                entry = entry,
                currency = "€",
                onClick = {
                    if (selectedEntry.value == entry) selectedEntry.value = null
                    else selectedEntry.value = entry
                },
                colorScheme = colorScheme,
                date = entry.date,
                viewDeleteButton = selectedEntry.value == entry,
                onDelete = {
                    onEntryDelete(it)
                },
                onHandled = {
                    onEntryHandled(it)
                },
                container = container
            )
        }

    }
}