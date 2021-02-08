package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomRadioButton(storage: MutableState<Int>, index: Int, text: String){
    Row{
        androidx.compose.material.RadioButton(selected = storage.value == index, onClick = { storage.value = index })
        Text(
            text = text,
            modifier = Modifier.clickable(onClick = { storage.value = index }).padding(start = 4.dp)
        )
    }
}