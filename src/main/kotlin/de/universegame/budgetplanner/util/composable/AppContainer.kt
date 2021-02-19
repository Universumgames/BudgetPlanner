package de.universegame.budgetplanner.util.composable

import androidx.compose.desktop.DesktopTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
/**
 * Basic composable element to contain the desktop application
 * */
fun AppContainer(modifier: Modifier = Modifier, appBg: Color, children: @Composable () -> Unit) =

    DesktopTheme {
        Surface(color = appBg) {
            Box(
                modifier = modifier
            ) {
                children()
            }
        }
    }

