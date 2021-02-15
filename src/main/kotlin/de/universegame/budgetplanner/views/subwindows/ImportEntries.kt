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
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.components.OneTimeBalanceEntry
import de.universegame.budgetplanner.util.composable.DefaultButton
import java.io.File
import java.time.LocalDate

@Composable
fun ImportEntriesView(
    colorScheme: BalanceListColors = BalanceListColors(),
    settings: Settings = Settings(),
    onSubmitClick: (List<OneTimeBalanceEntry>) -> Unit
) {
    val home = System.getProperty("user.home")
    val filePath = remember { mutableStateOf("$home\\Downloads\\") }
    val info = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(info.value, color = Color.White)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("File Path", color = Color.White)

            TextField(
                filePath.value,
                onValueChange = {
                    filePath.value = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.4f),
                shape = RoundedCornerShape(5.dp),
                activeColor = Color.White,
                textStyle = TextStyle(colorScheme.fontColor)
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            DefaultButton("Import") {
                val file = File(filePath.value)
                if (file.exists()) {
                    info.value = "Start import"
                    val text = file.readText()
                    val lines = text.split("\n")
                    val entries: MutableList<OneTimeBalanceEntry> = mutableListOf()
                    info.value = "Loading individual entries"
                    for (line in lines) {
                        if (line == lines[0]) continue
                        val values = line.split(";")
                        if (values.size < 15) continue
                        val dateString = values[1].replace("\"", "")
                        val usage = values[4].replace("\"", "")
                        val name = values[11].replace("\"", "")
                        val amountString = values[14].replace("\"", "")
                        val amount = amountString.replace(",", ".").toDouble()

                        entries.add(
                            OneTimeBalanceEntry(
                                amount = amount,
                                usage = usage,
                                containerId = 0,
                                name = name,
                                date = LocalDate.parse(dateString, settings.importDateFormat)
                            )
                        )

                    }
                    info.value = "Loaded all entries"
                    println(entries)
                    onSubmitClick(entries)
                } else info.value = "File does not exists"
            }
        }
    }
}