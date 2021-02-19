package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.SubWindowType
import de.universegame.budgetplanner.util.components.*
import de.universegame.budgetplanner.views.subwindows.AddOneTimeEntryView
import de.universegame.budgetplanner.views.subwindows.AddRecurringEntryView
import de.universegame.budgetplanner.views.subwindows.AddWalletView
import de.universegame.budgetplanner.views.subwindows.ImportEntriesView

@Composable
fun ContentView(
    container: BalanceContainer,
    settings: Settings,
    modifier: Modifier = Modifier,
    subWindow: SubWindowType = SubWindowType.NONE,
    tmp: Boolean,
    onAddOneTimeSubmit: (entry: OneTimeBalanceEntry) -> Unit,
    onAddRecurringSubmit: (entry: RecurringBalanceEntry) -> Unit,
    onImportSubmit: (entries: List<OneTimeBalanceEntry>) -> Unit,
    onAddWalletSubmit: (name: String) -> Unit,
    onEntryDelete: (IBalanceEntry) -> Unit,
    onEntryHandled: (IBalanceEntry) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val selectedMonth = remember { mutableStateOf(currentMonth(container)) }
        Surface(color = settings.colorScheme.widgetBgColor, shape = RoundedCornerShape(5.dp)) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.3f)) {
                BalanceListView(container) {
                    selectedMonth.value = it
                }
            }
        }
        Spacer(Modifier.size(4.dp))
        Column(modifier = Modifier.fillMaxHeight()) {
            Row(
                modifier = (if (subWindow != SubWindowType.NONE) Modifier.fillMaxWidth()
                    .fillMaxHeight(.5f) else Modifier.fillMaxSize())
            ) {
                Surface(color = settings.colorScheme.widgetBgColor, shape = RoundedCornerShape(5.dp)) {
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                        MonthOverviewView(
                            selectedMonth.value,
                            container = container,
                            onEntryDelete = {
                                onEntryDelete(it)
                            },
                            onEntryHandled = {
                                onEntryHandled(it)
                            })
                    }
                }
            }
            if (subWindow != SubWindowType.NONE) {
                Spacer(Modifier.size(4.dp))
                Row {
                    Surface(
                        color = settings.colorScheme.widgetBgColor,
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxHeight().fillMaxWidth()
                        ) {
                            when (subWindow) {
                                SubWindowType.ADD_ONETIME_ENTRY ->
                                    AddOneTimeEntryView(container = container) { entry: OneTimeBalanceEntry ->
                                        onAddOneTimeSubmit(entry)
                                    }
                                SubWindowType.ADD_RECURRING_ENTRY ->
                                    AddRecurringEntryView(container = container) { entry: RecurringBalanceEntry ->
                                        onAddRecurringSubmit(entry)
                                    }
                                SubWindowType.IMPORT ->
                                    ImportEntriesView { entries: List<OneTimeBalanceEntry> ->
                                        onImportSubmit(entries)
                                    }
                                SubWindowType.ADD_WALLET ->
                                    AddWalletView { name: String ->
                                        onAddWalletSubmit(name)
                                    }
                                SubWindowType.NONE -> {
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}