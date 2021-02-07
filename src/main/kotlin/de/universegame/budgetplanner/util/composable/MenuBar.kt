package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuBar(modifier: Modifier = Modifier, children: @Composable () -> Unit){
    Box(modifier=modifier.padding(0.dp)){
        Row() {
            children()
        }
    }
}