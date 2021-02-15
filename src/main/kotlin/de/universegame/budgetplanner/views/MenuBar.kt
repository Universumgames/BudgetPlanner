package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.SubWindowType
import de.universegame.budgetplanner.util.composable.MenuBar
import de.universegame.budgetplanner.util.composable.MenuBarButton

@Composable
fun MenuBarView(modifier: Modifier = Modifier, settings: Settings, onMenuBarCLick: (SubWindowType) -> Unit) {
    MenuBar(modifier = modifier.padding(0.dp)) {
        MenuBarButton(text = "+", fontSize = 20.sp, settings = settings) {
            onMenuBarCLick(SubWindowType.ADD_ONETIME_ENTRY)
        }
        MenuBarButton(text = "++", fontSize = 20.sp, settings = settings) {
            onMenuBarCLick(SubWindowType.ADD_RECURRING_ENTRY)
        }
        MenuBarButton(text = "Import", fontSize = 20.sp, settings = settings, bold = false) {
            onMenuBarCLick(SubWindowType.IMPORT)
        }
    }
}