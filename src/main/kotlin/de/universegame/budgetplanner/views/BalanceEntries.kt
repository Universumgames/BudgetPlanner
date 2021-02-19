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
import de.universegame.budgetplanner.util.composable.DefaultButton
import de.universegame.budgetplanner.util.short
import de.universegame.budgetplanner.util.toCurrencyString
import de.universegame.budgetplanner.util.toSimpleFullName
import java.time.LocalDate


@Composable
/**
 * Used in MonthOverview
 * */
fun BalanceEntryRow(
    modifier: Modifier = Modifier,
    entry: IBalanceEntry,
    container: BalanceContainer,
    date: LocalDate,
    currency: String,
    viewDeleteButton: Boolean,
    onClick: (IBalanceEntry) -> Unit,
    onDelete: (OneTimeBalanceEntry) -> Unit,
    onHandled: (IBalanceEntry) -> Unit,
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
                Text(entry.name.short(30), color = colorScheme.fontColor)
            }
            Column {
                Text(entry.usage.short(20), color = colorScheme.fontColor)
            }
            Column {
                Text(container.getWalletDataById(entry.containerId).name, color = colorScheme.fontColor)
            }
            if (viewDeleteButton) {
                Column {
                    if (entry.type == EntryType.ONE_TIME)
                        DefaultButton("Delete") {
                            onDelete(entry as OneTimeBalanceEntry)
                        }
                    else if (entry.type == EntryType.RECURRING)
                        DefaultButton("Booked") {
                            onHandled(entry)
                        }
                }
            }
        }
    }
    Spacer(Modifier.size(3.dp))
}

@Composable
/**
 * Used in BalanceList
 * Displays the total change of money for that year and the remaining money at the end of the year
 * */
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
            val totalChange = entry.getChange(timedList)
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
/**
 * Used in BalanceList
 * Displays the total change of money for that month and the remaining money at the end of the month
 * */
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
            Text(entry.yearMonth.toSimpleFullName(), color = colorScheme.fontColor)
            val totalChange = entry.getChange(timedList)
            val total = container.totalTil(entry.yearMonth)
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