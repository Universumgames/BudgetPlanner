package de.universegame.budgetplanner.views.subwindows

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
import de.universegame.budgetplanner.util.components.BalanceContainer
import de.universegame.budgetplanner.util.components.Interval
import de.universegame.budgetplanner.util.components.RecurringBalanceEntry
import de.universegame.budgetplanner.util.composable.*
import java.time.LocalDate

@Composable
/**
 * Subwindow for adding reccuring entry
 * */
fun AddRecurringEntryView(
    colorScheme: BalanceListColors = BalanceListColors(),
    container: BalanceContainer,
    onSubmitClick: (RecurringBalanceEntry) -> Unit
) {
    ScrollColumn(Modifier.fillMaxSize().padding(5.dp)) {
        val entry = remember { mutableStateOf(RecurringBalanceEntry(0.0, "Not defined", 0, "")) }
        val amountInput = remember { mutableStateOf("0.0") }
        val error = remember { mutableStateOf("") }
        val selectedInterval = remember { mutableStateOf(0) }
        val date1Input = remember { mutableStateOf(LocalDate.now()) }
        val date1Set = remember { mutableStateOf(false) }
        val date2Input = remember { mutableStateOf(LocalDate.now()) }
        val date2Set = remember { mutableStateOf(false) }
        val usage = remember { mutableStateOf("") }
        val name = remember { mutableStateOf("") }
        val walletData = remember { mutableStateOf(container.walletNames[0]) }

        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            TextField(
                amountInput.value,
                onValueChange = { it: String ->
                    amountInput.value = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.4f),
                shape = RoundedCornerShape(5.dp),
                textStyle = TextStyle(colorScheme.fontColor)
            )
            DefaultButton(onClick = {
                var amount = 0.0
                try {
                    error.value = ""
                    amount = amountInput.value.replace(",", ".").toDouble()
                } catch (e: Exception) {
                    error.value = "Not a number"
                    println(e.stackTraceToString())
                }
                entry.value.amount = amount
                entry.value.usage = usage.value
                entry.value.name = name.value
                entry.value.startTime = date1Input.value
                entry.value.endTime = date2Input.value
                entry.value.interval = Interval.values()[selectedInterval.value]
                entry.value.containerId = walletData.value.id
                onSubmitClick(entry.value)
            }, text = "Submit")
        }

        WalletInput(container, walletData)

        SimpleFlowRow(modifier = Modifier.fillMaxWidth(), horizontalGap = 2, verticalGap = 5) {
            //Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                for (i in Interval.values()) {
                    CustomRadioButton(selectedInterval, i.id, i.prettyName, colorScheme.fontColor)
                    Spacer(Modifier.width(2.dp))
                }
            //}
        }
        Spacer(Modifier.size(3.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Row {
                Text("Usage", color = Color.White)

                TextField(
                    usage.value,
                    onValueChange = {it: String ->
                        usage.value = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.4f),
                    shape = RoundedCornerShape(5.dp),
                    textStyle = TextStyle(colorScheme.fontColor)
                )
            }
        }
        Spacer(Modifier.size(3.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Row {
                Text("Name", color = Color.White)

                TextField(
                    name.value,
                    onValueChange = { it: String ->
                        name.value = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.4f),
                    shape = RoundedCornerShape(5.dp),
                    textStyle = TextStyle(colorScheme.fontColor)
                )
            }
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
