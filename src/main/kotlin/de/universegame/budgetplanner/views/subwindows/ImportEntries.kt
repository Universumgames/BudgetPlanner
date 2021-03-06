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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.plus
import androidx.compose.ui.input.key.shortcuts
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import de.universegame.budgetplanner.util.BalanceListColors
import de.universegame.budgetplanner.util.Settings
import de.universegame.budgetplanner.util.components.BalanceContainer
import de.universegame.budgetplanner.util.components.OneTimeBalanceEntry
import de.universegame.budgetplanner.util.composable.DefaultButton
import de.universegame.budgetplanner.util.composable.WalletInput
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.io.File
import java.time.LocalDate

@Composable
/**
 * Subwindow to import entries from a file
 * (special formatted csv file required currently: "CSV-CAMT-Format")
 * */
fun ImportEntriesView(
    colorScheme: BalanceListColors = BalanceListColors(),
    container: BalanceContainer,
    settings: Settings = Settings(),
    onSubmitClick: (List<OneTimeBalanceEntry>) -> Unit
) {
    val home = System.getProperty("user.home")
    val filePath = remember { mutableStateOf("$home\\Downloads\\") }
    val info = remember { mutableStateOf("") }
    val walletData = remember { mutableStateOf(container.walletNames[0]) }

    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(info.value, color = Color.White)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("File Path", color = Color.White)

            TextField(
                filePath.value,
                onValueChange = {it: String ->
                    filePath.value = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.4f).shortcuts {
                    on(Key.CtrlLeft + Key.V ) {
                        val c = Toolkit.getDefaultToolkit().systemClipboard
                        filePath.value += c.getData(DataFlavor.stringFlavor) as String
                    }
                    on(Key.CtrlRight + Key.V ) {
                        val c = Toolkit.getDefaultToolkit().systemClipboard
                        filePath.value += c.getData(DataFlavor.stringFlavor) as String
                    }
                },
                shape = RoundedCornerShape(5.dp),
                textStyle = TextStyle(colorScheme.fontColor)
            )
        }

        WalletInput(container, walletData)

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
                                containerId = walletData.value.id,
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