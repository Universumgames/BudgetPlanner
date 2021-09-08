package de.universegame.budgetplanner.views.subwindows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.BalanceListColors
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.composable.DefaultButton

@Composable
/**
 * Subwindow for adding new money location
 * */
fun AddWalletView(
    colorScheme: BalanceListColors = BalanceListColors(),
    settings: Settings = Settings(),
    onSubmit: (name: String) -> Unit
) {
    val name = remember { mutableStateOf("newName") }
    Column(modifier = Modifier.fillMaxSize().padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("New Wallet Name", color = Color.White)

            TextField(
                name.value,
                onValueChange = {it: String ->
                    name.value = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.4f),
                shape = RoundedCornerShape(5.dp),
                textStyle = TextStyle(colorScheme.fontColor)
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            DefaultButton("Create") {
                onSubmit(name.value)
            }
        }
    }
}