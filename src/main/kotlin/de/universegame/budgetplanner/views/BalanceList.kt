package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.universegame.budgetplanner.util.composable.ScrollColumn

@Composable
fun BalanceListView() {
    ScrollColumn(modifier = Modifier.fillMaxSize()) {
        for (i in 0..50) {
            Row {
                Column(Modifier.align(Alignment.CenterVertically)) {
                    val count = remember { mutableStateOf(i) }
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            count.value++
                        }) {
                        Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
                    }
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            count.value = i
                        }) {
                        Text("Reset")
                    }
                }
            }
        }
    }
}