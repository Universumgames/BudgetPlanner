package de.universegame.budgetplanner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.composable.AppContainer
import de.universegame.budgetplanner.views.ContentView
import de.universegame.budgetplanner.views.MenuBarView


val DarkTheme = darkColors(surface = Color(0xff1d1d1d), background = Color(0xff2d2d2d), primary = Color(0xff5d5d5d))


@Composable
fun AppView() {
    AppContainer(
        colors = DarkTheme,
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
    ) {
        ContentView()
        MenuBarView()
    }
}

