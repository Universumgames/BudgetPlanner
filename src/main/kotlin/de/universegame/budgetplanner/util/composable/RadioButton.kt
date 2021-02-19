package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
/**
 * Custom composable to create a Radio button
 * */
fun CustomRadioButton(
    storage: MutableState<Int>,
    index: Int,
    text: String,
    fontColor: Color = Color.White,
    radioColor: RadioButtonColors = RadioButtonDefaults.colors(selectedColor = Color.Blue, unselectedColor = Color.White, disabledColor = Color.Black)
) {
    Row {
        RadioButton(
            selected = storage.value == index,
            onClick = { storage.value = index },
            colors = radioColor
        )
        Text(
            text = text,
            modifier = Modifier.clickable(onClick = { storage.value = index }).padding(start = 4.dp),
            color = fontColor
        )
    }
}