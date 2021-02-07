package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.composable.MenuBar
import de.universegame.budgetplanner.util.composable.MenuBarButton

@Composable
fun MenuBarView(modifier: Modifier = Modifier) {
    MenuBar(modifier = modifier.padding(0.dp)) {
        val count = remember { mutableStateOf(0) }
        MenuBarButton(onClick = {
            count.value++
        }, text = "${count.value}")
    }
}