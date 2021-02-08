package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonBgColor: Color = Color(0xff5d5d5d),
    fontColor: Color = Color.White,
    fontSize: TextUnit = 10.sp,
    style: TextStyle = TextStyle.Default,
    shape: Shape = RoundedCornerShape(5.dp),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonBgColor)
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = fontColor,
            style = style
        )
    }
}