package de.universegame.budgetplanner.util.composable

import androidx.compose.desktop.DesktopTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppContainer(modifier: Modifier = Modifier, colors: Colors = lightColors(), children: @Composable () -> Unit){
    MaterialTheme(
        colors = colors
    ) {
        DesktopTheme {
            Surface(color = colors.background) {
                Box(
                    modifier = modifier
                ) {
                    children()
                }
            }
        }
    }
}