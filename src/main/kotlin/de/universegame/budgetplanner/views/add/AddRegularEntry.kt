package de.universegame.budgetplanner.views.add

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
import de.universegame.budgetplanner.util.BalanceListColors
import de.universegame.budgetplanner.util.components.Interval
import de.universegame.budgetplanner.util.components.RegularBalanceEntry
import de.universegame.budgetplanner.util.composable.*
import java.time.LocalDate

//ToDo try to make textfields smaller

@Composable
fun AddRegularEntryView(
    colorScheme: BalanceListColors = BalanceListColors(),
    onSubmitClick: (RegularBalanceEntry) -> Unit
) {
    ScrollColumn(Modifier.fillMaxSize().padding(5.dp)) {
        val entry = remember { mutableStateOf(RegularBalanceEntry(0.0, "Not defined", 0)) }
        val amountInput = remember { mutableStateOf("0.0") }
        val error = remember { mutableStateOf("") }
        val selectedInterval = remember { mutableStateOf(0) }
        val date1Input = remember { mutableStateOf(LocalDate.now()) }
        val date1Set = remember { mutableStateOf(false) }
        val date2Input = remember { mutableStateOf(LocalDate.now()) }
        val date2Set = remember { mutableStateOf(false) }
        val usage = remember { mutableStateOf("") }

        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            TextField(
                amountInput.value,
                onValueChange = {
                    amountInput.value = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.4f),
                shape = RoundedCornerShape(5.dp),
                activeColor = Color.White,
                textStyle = TextStyle(colorScheme.fontColor)
            )
            DefaultButton(onClick = {
                var amount = 0.0
                try {
                    error.value = ""
                    amount = amountInput.value.toDouble()
                } catch (e: Exception) {
                    error.value = "Not a number"
                    println(e.stackTraceToString())
                }
                entry.value.amount = amount
                entry.value.usage = usage.value
                onSubmitClick(entry.value)
            }, text = "Submit")
        }

        SimpleFlowRow(modifier = Modifier.fillMaxWidth(), horizontalGap = 2.dp, verticalGap = 5.dp) {
            for (i in Interval.values()) {
                CustomRadioButton(selectedInterval, i.id, i.prettyName, colorScheme.fontColor)
                Spacer(Modifier.width(2.dp))
            }
        }
        Spacer(Modifier.size(3.dp))
        Row {
            Text("Usage", color = Color.White)

            TextField(
                usage.value,
                onValueChange = {
                    usage.value = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.4f),
                shape = RoundedCornerShape(5.dp),
                activeColor = Color.White,
                textStyle = TextStyle(colorScheme.fontColor)
            )
        }
        Spacer(Modifier.size(3.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Start date", color = colorScheme.fontColor)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            if (!date1Set.value)
                SimpleDateInput { date1Input.value = it; date1Set.value = true }
            else Text(date1Input.value.toString(), color = colorScheme.fontColor)
        }
        Spacer(Modifier.size(3.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("End date", color = colorScheme.fontColor)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            if (!date2Set.value)
                SimpleDateInput { date2Input.value = it; date2Set.value = true }
            else Text(date2Input.value.toString(), color = colorScheme.fontColor)
        }

        Row {
            Text(error.value, color = Color.Red)
        }
    }
}