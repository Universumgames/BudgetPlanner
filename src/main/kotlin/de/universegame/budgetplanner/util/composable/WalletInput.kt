package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import de.universegame.budgetplanner.util.BalanceListColors
import de.universegame.budgetplanner.util.components.BalanceContainer
import de.universegame.budgetplanner.util.components.WalletData

@Composable
/**
 * Simple composable element for promting the user to input a wallet id, next to the user input thee corresponding walletData name will be presented
 * */
fun WalletInput(container: BalanceContainer, walletData: MutableState<WalletData>, colorScheme: BalanceListColors = BalanceListColors(), modifier: Modifier = Modifier){
    val walletIDString = remember { mutableStateOf("0") }
    Row(modifier = modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        Row {

            Text("Wallet ID", color = Color.White)

            TextField(
                walletIDString.value,
                onValueChange = {
                    walletIDString.value = it
                    if(it.isNotEmpty()){
                        try{
                            walletData.value = container.getWalletDataById(walletIDString.value.toInt())
                        }catch(e: Exception){}
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.4f),
                shape = RoundedCornerShape(5.dp),
                activeColor = Color.White,
                textStyle = TextStyle(colorScheme.fontColor)
            )

            Text(walletData.value.name, color = Color.White)
        }
    }
}