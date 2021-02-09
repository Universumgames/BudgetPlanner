package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun SimpleDateInput(modifier: Modifier = Modifier, fontColor: Color = Color.White, onSubmit: (LocalDate) -> Unit) {
    SimpleFlowRow(modifier.fillMaxWidth().padding(5.dp), horizontalGap = 2.dp, verticalGap = 5.dp) {
        val localDate = LocalDate.now()
        val day = remember { mutableStateOf(localDate.dayOfMonth.toString()) }
        val month = remember { mutableStateOf(localDate.monthValue.toString()) }
        val year = remember { mutableStateOf(localDate.year.toString()) }
        Text("Day", color = fontColor)
        Spacer(Modifier.size(3.dp))
        TextField(
            day.value,
            onValueChange = {
                day.value = it.replace(" ", "")
            },
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.height(50.dp).width(80.dp),
            activeColor = Color.White,
            textStyle = TextStyle(fontColor)
        )
        Spacer(Modifier.size(3.dp))

        Text("Month", color = fontColor)
        Spacer(Modifier.size(3.dp))
        TextField(
            month.value,
            onValueChange = {
                month.value = it.replace(" ", "")
            },
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.height(50.dp).width(80.dp),
            activeColor = Color.White,
            textStyle = TextStyle(fontColor)
        )
        Spacer(Modifier.size(3.dp))

        Text("Year", color = fontColor)
        Spacer(Modifier.size(3.dp))
        TextField(
            year.value,
            onValueChange = {
                year.value = it.replace(" ", "")
            },
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.height(50.dp).width(80.dp),
            activeColor = Color.White,
            textStyle = TextStyle(fontColor)
        )
        DefaultButton(onClick = {
            try {
                val date = LocalDate.of(year.value.toInt(), month.value.toInt(), day.value.toInt())
                onSubmit(date)
            } catch (e: Exception) {
                println(e.stackTraceToString())
            }
        }, text = "Set Date")

    }
}