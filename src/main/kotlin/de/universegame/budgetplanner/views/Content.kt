package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.AddEntryType
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.SubWindowType
import de.universegame.budgetplanner.util.components.BalanceContainer
import de.universegame.budgetplanner.util.components.IBalanceEntry
import de.universegame.budgetplanner.util.components.currentMonth


//ToDo split add-one-time-entry and regular entry into seperate menus

@Composable
fun ContentView(
    container: BalanceContainer,
    settings: Settings,
    modifier: Modifier = Modifier,
    subWindow: SubWindowType = SubWindowType.NONE,
    onAddSubmit: (entry: IBalanceEntry, addEntryType: AddEntryType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val selectedMonth = remember { mutableStateOf(currentMonth(container)) }
        Surface(color = settings.colorScheme.widgetBgColor, shape = RoundedCornerShape(5.dp)) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.6f)) {
                BalanceListView(container) {
                    selectedMonth.value = it
                }
            }
        }
        Spacer(Modifier.size(4.dp))
        Column(modifier = Modifier.fillMaxHeight()) {
            Row(
                modifier = (if (subWindow == SubWindowType.ADD_ENTRY) Modifier.fillMaxWidth()
                    .fillMaxHeight(.5f) else Modifier.fillMaxSize())
            ) {
                Surface(color = settings.colorScheme.widgetBgColor, shape = RoundedCornerShape(5.dp)) {
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                        MonthOverviewView(selectedMonth.value, container = container)
                    }
                }
            }
            if (subWindow == SubWindowType.ADD_ENTRY) {
                Spacer(Modifier.size(4.dp))
                Row {
                    Surface(color = settings.colorScheme.widgetBgColor, shape = RoundedCornerShape(5.dp)) {
                        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                            AddEntryView { entry: IBalanceEntry, addEntryType: AddEntryType ->
                                onAddSubmit(entry, addEntryType)
                            }
                        }
                    }
                }
            }
        }
    }
}