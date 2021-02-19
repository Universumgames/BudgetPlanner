package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.SubWindowType
import de.universegame.budgetplanner.util.components.*
import de.universegame.budgetplanner.util.composable.AppContainer

@Composable
        /**
         * Contains al important elements for the application, like MenuBar, MonthOverviwe, BalanceList, and all subwindows
         * */
fun AppView(mutableContainer: MutableState<BalanceContainer>, settings: Settings) {
    AppContainer(
        appBg = settings.colorScheme.appBackground,
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
    ) {
        val subWindow = remember { mutableStateOf(SubWindowType.NONE) }
        val tmp = remember { mutableStateOf(false) }
        ContentView(
            subWindow = subWindow.value,
            container = mutableContainer.value,
            tmp = tmp.value,
            settings = settings, onAddOneTimeSubmit = { entry: OneTimeBalanceEntry ->
                subWindow.value = SubWindowType.NONE
                mutableContainer.value.addOneTimeEntry(entry)
                println("added " + entry.amount + " to " + entry.date)
                saveBalanceContainer(mutableContainer.value, settings.dataFileName, settings.jsonSerializer)

            }, onAddRecurringSubmit = { entry: RecurringBalanceEntry ->
                subWindow.value = SubWindowType.NONE
                mutableContainer.value.addRecurringEntry(entry)
                println("added recurring")
                saveBalanceContainer(mutableContainer.value, settings.dataFileName, settings.jsonSerializer)
            }, onImportSubmit = { entries: List<OneTimeBalanceEntry> ->
                subWindow.value = SubWindowType.NONE
                mutableContainer.value.addOneTimeEntryList(entries)
                saveBalanceContainer(mutableContainer.value, settings.dataFileName, settings.jsonSerializer)
            }, onAddWalletSubmit = { name: String ->
                subWindow.value = SubWindowType.NONE
                mutableContainer.value.addWallet(name)
                saveBalanceContainer(mutableContainer.value, settings.dataFileName, settings.jsonSerializer)
                println("added wallet $name")
            },
            onEntryDelete = { entry: IBalanceEntry ->
                if (entry is OneTimeBalanceEntry)
                    if (mutableContainer.value.deleteOneTimeEntry(entry))
                        println("deleted $entry")
                tmp.value = !tmp.value
                saveBalanceContainer(mutableContainer.value, settings.dataFileName, settings.jsonSerializer)
            }, onEntryHandled = { entry: IBalanceEntry ->
                if (mutableContainer.value.setRecurringEntryHandled(entry as OneTimeBalanceEntry))
                    println("booked ${entry.toSerializable()}")
                mutableContainer.value.walletNames.sortBy { it.id }
                tmp.value = !tmp.value

                saveBalanceContainer(mutableContainer.value, settings.dataFileName, settings.jsonSerializer)
            })
        MenuBarView(settings = settings) {
            if (subWindow.value == it) subWindow.value = SubWindowType.NONE
            else subWindow.value = it
        }
    }
}

