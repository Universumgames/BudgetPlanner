package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ScrollColumn(modifier: Modifier = Modifier, children: @Composable () -> Unit) {
    Box(modifier = modifier) {
        val state = rememberScrollState(0f)
        ScrollableColumn(
            modifier = modifier.fillMaxSize(),
            scrollState = state
        ) {
            children()
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(state)
        )
    }
}