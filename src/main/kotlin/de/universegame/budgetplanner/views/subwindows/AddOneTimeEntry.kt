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
import de.universegame.budgetplanner.util.components.OneTimeBalanceEntry
import de.universegame.budgetplanner.util.composable.DefaultButton
import de.universegame.budgetplanner.util.composable.ScrollColumn
import de.universegame.budgetplanner.util.composable.SimpleDateInput
import de.universegame.budgetplanner.util.composable.WalletInput
import java.time.LocalDate

@Composable
/**
 * Subwindow to add an OneTimeEntry
 * */
fun AddOneTimeEntryView(
    colorScheme: BalanceListColors = BalanceListColors(),
    container: BalanceContainer,
    onSubmitClick: (OneTimeBalanceEntry) -> Unit
) {
    ScrollColumn(Modifier.fillMaxSize().padding(5.dp)) {
        val entry = remember { mutableStateOf(OneTimeBalanceEntry(0.0, "Not defined", 0, "")) }
        val amountInput = remember { mutableStateOf("0.0") }
        val error = remember { mutableStateOf("") }
        val dateInput = remember { mutableStateOf(LocalDate.now()) }
        val dateSet = remember { mutableStateOf(false) }
        val usage = remember { mutableStateOf("") }
        val name = remember { mutableStateOf("") }
        val walletData = remember { mutableStateOf(container.walletNames[0]) }

        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            TextField(
                amountInput.value.toString(),
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
                entry.value.date = dateInput.value
                try{
                entry.value.containerId = walletData.value.id
                }catch(e: Exception){}
                if (amount > 0)
                    onSubmitClick(entry.value)
            }, text = "Submit")
        }

        WalletInput(container, walletData)

        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Row {

                Text("Usage", color = Color.White)

                TextField(
                    usage.value,
                    onValueChange = { it: String ->
                        usage.value = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.4f),
                    shape = RoundedCornerShape(5.dp),
                    textStyle = TextStyle(colorScheme.fontColor)
                )
            }
        }

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

        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Transactiondate", color = colorScheme.fontColor)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            if (!dateSet.value)
                SimpleDateInput { dateInput.value = it; dateSet.value = true }
            else Text(dateInput.value.toString(), color = colorScheme.fontColor)
        }

        Row {
            Text(error.value, color = Color.Red)
        }
    }
}