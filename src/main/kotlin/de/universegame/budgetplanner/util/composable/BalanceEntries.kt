package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.universegame.budgetplanner.util.BalanceListColors
import de.universegame.budgetplanner.util.components.IBalanceEntry
import de.universegame.budgetplanner.util.components.MonthlyEntries
import de.universegame.budgetplanner.util.components.RegularBalanceEntry
import de.universegame.budgetplanner.util.components.YearlyEntries
import de.universegame.budgetplanner.util.toSimpleFullName

@Composable
fun BalanceEntryRow(modifier: Modifier = Modifier, entry: IBalanceEntry, currency: String, onClick: (IBalanceEntry) -> Unit, colorScheme: BalanceListColors = BalanceListColors()) {
    Surface(color = colorScheme.oneTimeEntryBgColor) {
        Row(modifier = modifier.fillMaxWidth().clickable { onClick(entry) }, Arrangement.SpaceEvenly) {
            Column {
                Text(
                    String.format("%.2f", entry.amount) + currency,
                    color = if (entry.amount < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor
                )
            }
            Column {
                Text(entry.usage, color = colorScheme.fontColor)
            }
            Column {
                Text(entry.containerId.toString(), color = colorScheme.fontColor)
            }
        }
    }
}

@Composable
fun YearOverviewRow(
    modifier: Modifier = Modifier,
    colorScheme: BalanceListColors = BalanceListColors(),
    entry: YearlyEntries,
    currency: String,
    timedList: MutableList<RegularBalanceEntry>,
    onClick: () -> Unit
) {
    Surface(color = colorScheme.yearOverviewBgColor) {
        Row(modifier.fillMaxWidth().clickable { onClick() }, Arrangement.SpaceEvenly) {
            Text(entry.year.value.toString(), color = colorScheme.fontColor)
            val total = entry.total(timedList)
            Text(String.format("%.2f", total) + currency, color = if(total < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor)
        }
    }
}

@Composable
fun MonthOverviewRow(
    modifier: Modifier = Modifier,
    colorScheme: BalanceListColors = BalanceListColors(),
    entry: MonthlyEntries,
    currency: String,
    timedList: MutableList<RegularBalanceEntry>,
    onClick: () -> Unit
) {
    Surface(color = colorScheme.monthOverviewBgColor) {
        Row(modifier.fillMaxWidth().clickable { onClick() }, Arrangement.SpaceEvenly) {
            Text(entry.month.toSimpleFullName())
            val total = entry.total(timedList)
            Text(String.format("%.2f", total) + currency, color = if(total < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor)
        }
    }
}