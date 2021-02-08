package de.universegame.budgetplanner.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.AddEntryType
import de.universegame.budgetplanner.util.BalanceListColors
import de.universegame.budgetplanner.util.components.IBalanceEntry
import de.universegame.budgetplanner.util.components.Interval
import de.universegame.budgetplanner.util.components.OneTimeBalanceEntry
import de.universegame.budgetplanner.util.composable.CustomRadioButton
import de.universegame.budgetplanner.util.composable.DefaultButton
import de.universegame.budgetplanner.util.composable.SimpleDateInput
import de.universegame.budgetplanner.util.composable.SimpleFlowRow
import java.time.LocalDate


@Composable
fun AddEntryView(
    colorScheme: BalanceListColors = BalanceListColors(),
    onSubmitClick: (IBalanceEntry, AddEntryType) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(5.dp)) {
        val entryType = remember { mutableStateOf(AddEntryType.OneTime) }
        val entry: MutableState<IBalanceEntry> = remember { mutableStateOf(OneTimeBalanceEntry(0.0, "Not defined", 0)) }
        val amountInput = remember { mutableStateOf("0.0") }
        val error = remember { mutableStateOf("") }
        val selectedInterval = remember { mutableStateOf(0) }
        val date1Input = remember { mutableStateOf(LocalDate.now()) }
        val date1Set = remember { mutableStateOf(false) }
        val date2Input = remember { mutableStateOf(LocalDate.now()) }
        val date2Set = remember { mutableStateOf(false) }

        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            TextField(amountInput.value.toString(), onValueChange = {
                amountInput.value = it
            }, singleLine = true, modifier = Modifier.fillMaxWidth(0.4f), shape = RoundedCornerShape(5.dp), activeColor = Color.White, textStyle = TextStyle(colorScheme.fontColor))
            DefaultButton(onClick = {
                var amount = 0.0
                try {
                    error.value = ""
                    amount = amountInput.value.toDouble()
                } catch (e: Exception) {
                    error.value = "Not a number"
                    println(e.stackTraceToString())
                }
                println(amountInput.value)
                //onSubmitClick(entry.value, entryType.value)
            }, text = "Submit")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DefaultButton(
                onClick = {
                    when (entryType.value) {
                        AddEntryType.Regular -> entryType.value = AddEntryType.OneTime
                        AddEntryType.OneTime -> entryType.value = AddEntryType.Regular
                    }
                },
                text =
                when (entryType.value) {
                    AddEntryType.OneTime -> "One Time"
                    AddEntryType.Regular -> "Regular"
                    else -> ""
                }
            )

        }
        if (entryType.value == AddEntryType.Regular) {
            SimpleFlowRow(modifier = Modifier.fillMaxWidth(), horizontalGap = 2.dp, verticalGap = 5.dp) {
                for (i in Interval.values()) {
                    CustomRadioButton(selectedInterval, i.id, i.prettyName)
                    Spacer(Modifier.width(2.dp))
                }
            }
            Spacer(Modifier.size(3.dp))
            Text("Start date", color = colorScheme.fontColor)
            if (!date1Set.value)
                SimpleDateInput { date1Input.value = it; date1Set.value = true }
            else Text(date1Input.value.toString())
            Spacer(Modifier.size(3.dp))
            Text("End date", color = colorScheme.fontColor)
            if (!date2Set.value)
                SimpleDateInput { date2Input.value = it; date2Set.value = true }
            else Text(date1Input.value.toString())
        } else {
            Text("Transactiondate", color = colorScheme.fontColor)
            if (!date1Set.value)
                SimpleDateInput { date1Input.value = it; date1Set.value = true }
            else Text(date1Input.value.toString())
        }
        Row {
            Text(error.value, color = Color.Red)
        }
    }

}