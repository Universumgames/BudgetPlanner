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
import java.time.Year

@Composable
/**
 * Simple composable element for a user input, simple way to prompt the user to input a date
 * */
fun SimpleDateInput(modifier: Modifier = Modifier, fontColor: Color = Color.White, onSubmit: (LocalDate) -> Unit) {
    SimpleFlowRow(modifier.padding(5.dp), horizontalGap = 2.dp, verticalGap = 5.dp) {
        val localDate = LocalDate.now()
        val day = remember { mutableStateOf(localDate.dayOfMonth.toString()) }
        val month = remember { mutableStateOf(localDate.monthValue.toString()) }
        val year = remember { mutableStateOf(localDate.year.toString()) }
        Text("Day", color = fontColor)
        Spacer(Modifier.size(3.dp))
        TextField(
            day.value,
            onValueChange = {
                val t = it.replace(" ", "")
                if (t.isNotEmpty()) {
                    val cut = t.substring(0, if (t.length < 2) t.length else 2)
                    try {
                        var i = cut.toIntOrNull() ?: 1
                        if (i > 31) i = 31
                        if (i < 1) i = 1
                        day.value = i.toString()
                    } catch (e: Exception) {
                    }
                } else day.value = t
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
                val t = it.replace(" ", "")
                if (t.isNotEmpty()) {
                    val cut = t.substring(0, if (t.length < 2) t.length else 2)
                    try {
                        var i = cut.toIntOrNull() ?: 1
                        if (i > 12) i = 12
                        if (i < 1) i = 1
                        month.value = i.toString()
                    } catch (e: Exception) {
                    }
                } else month.value = t
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
                val t = it.replace(" ", "")
                if (t.isNotEmpty() && t.length > 3) {
                    val cut = t.substring(0, if (t.length < 4) t.length else 4)
                    try {
                        var i = cut.toIntOrNull() ?: Year.now().value
                        if (i > Year.now().value + 100) i = Year.now().value + 100
                        if (i < Year.now().value - 100) i = Year.now().value - 100
                        year.value = i.toString()
                    } catch (e: Exception) {
                    }
                } else year.value = it
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