package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContentView() {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(5.dp)) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.5f)) {
                BalanceListView()
            }
        }
        Spacer(Modifier.size(4.dp))
        Surface(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(5.dp)) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                MonthOverviewView()
            }
        }
    }
}