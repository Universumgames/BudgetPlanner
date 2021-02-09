package de.universegame.budgetplanner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.AddEntryType
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.SubWindowType
import de.universegame.budgetplanner.util.components.BalanceContainer
import de.universegame.budgetplanner.util.components.IBalanceEntry
import de.universegame.budgetplanner.util.components.OneTimeBalanceEntry
import de.universegame.budgetplanner.util.composable.AppContainer
import de.universegame.budgetplanner.views.ContentView
import de.universegame.budgetplanner.views.MenuBarView


val DarkTheme = darkColors(surface = Color(0xff1d1d1d), background = Color(0xff2d2d2d), primary = Color(0xff5d5d5d))


@Composable
fun AppView(mutableContainer: MutableState<BalanceContainer>, settings: Settings) {
    AppContainer(
        appBg = settings.colorScheme.appBackground,
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
    ) {
        val subWindow = remember { mutableStateOf(SubWindowType.NONE) }
        ContentView(
            subWindow = subWindow.value,
            container = mutableContainer.value,
            settings = settings
        ) { entry: IBalanceEntry, addEntryType: AddEntryType ->
            subWindow.value = SubWindowType.NONE
            if (addEntryType == AddEntryType.OneTime) {
                mutableContainer.value.addOneTimeEntry(entry as OneTimeBalanceEntry)
                println("added " + entry.amount + " to " + entry.date)
            }
        }
        MenuBarView(settings = settings) {
            subWindow.value = it
        }
    }
}

