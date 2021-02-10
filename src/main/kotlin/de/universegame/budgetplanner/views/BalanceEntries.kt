package de.universegame.budgetplanner.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.BalanceListColors
import de.universegame.budgetplanner.util.components.*
import de.universegame.budgetplanner.util.toCurrencyString
import de.universegame.budgetplanner.util.toSimpleFullName
import java.time.LocalDate


@Composable
fun BalanceEntryRow(
    modifier: Modifier = Modifier,
    entry: IBalanceEntry,
    date: LocalDate,
    currency: String,
    onClick: (IBalanceEntry) -> Unit,
    colorScheme: BalanceListColors = BalanceListColors()
) {
    Surface(color = if (entry.type == EntryType.ONE_TIME) colorScheme.oneTimeEntryBgColor else colorScheme.recurringEntryBgColor) {
        Row(modifier = modifier.fillMaxWidth().clickable { onClick(entry) }, Arrangement.SpaceEvenly) {
            Column {
                Text(date.toString(), color = colorScheme.fontColor)
            }
            Column {
                Text(
                    entry.amount.toCurrencyString(currency),
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
    Spacer(Modifier.size(3.dp))
}

@Composable
fun YearOverviewRow(
    modifier: Modifier = Modifier,
    colorScheme: BalanceListColors = BalanceListColors(),
    entry: YearlyEntries,
    currency: String,
    timedList: MutableList<RecurringBalanceEntry>,
    container: BalanceContainer,
    onClick: () -> Unit
) {
    Surface(
        color = colorScheme.yearOverviewBgColor,
        modifier = modifier.padding(1.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(modifier.fillMaxWidth().clickable { onClick() }, Arrangement.SpaceEvenly) {
            Text(entry.year.value.toString(), color = colorScheme.fontColor)
            val totalChange = entry.total(timedList)
            val total = container.totalTil(entry.year)
            Box {
                Row {
                    Text(
                        totalChange.toCurrencyString(currency),
                        color = if (totalChange < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor
                    )
                    Text(" in change", color = colorScheme.fontColor)
                }
            }
            Box {
                Row {
                    Text(
                        total.toCurrencyString(currency, false),
                        color = if (total < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor
                    )
                    Text(" in total", color = colorScheme.fontColor)
                }
            }
        }
    }
}

@Composable
fun MonthOverviewRow(
    modifier: Modifier = Modifier,
    colorScheme: BalanceListColors = BalanceListColors(),
    entry: MonthlyEntries,
    currency: String,
    timedList: MutableList<RecurringBalanceEntry>,
    container: BalanceContainer,
    onClick: () -> Unit,
) {
    Surface(color = colorScheme.monthOverviewBgColor) {
        Row(modifier.fillMaxWidth().clickable { onClick() }, Arrangement.SpaceEvenly) {
            Text(entry.month.toSimpleFullName(), color = colorScheme.fontColor)
            val totalChange = entry.total(timedList)
            val total = container.totalTil(entry.month)
            Text(
                totalChange.toCurrencyString(currency),
                color = if (totalChange < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor
            )
            Text(
                total.toCurrencyString(currency, false),
                color = if (total < 0) colorScheme.negativeEntryFontColor else colorScheme.positiveEntryFontColor
            )
        }
    }
}