package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.universegame.budgetplanner.util.Settings

/*private fun tabShape(rad: Dp) = GenericShape { size, direction ->
    moveTo(-1f, 0f)
    /*lineTo(size.width, 0f)
    lineTo(size.width, size.height - rad.value)
    lineTo(size.width - rad.value, size.height)
    lineTo(0f, size.height)
    moveTo(size.width - rad.value, size.height - rad.value)*/
    val offset = rad.value * 1.1f
    val newSize = Size(size.width + offset, size.height)
    addRoundRect(
        RoundRect(
            Rect(Offset.Zero, size),
            bottomLeft = CornerRadius(rad.value),
            bottomRight = CornerRadius(rad.value),
        )
    )
}*/

@Composable
/**
 * Composable element to be used as a button in thhe menu bar
 * */
fun MenuBarButton(
    settings: Settings,
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 10.sp,
    bold: Boolean = true,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        fontSize = fontSize,
        modifier = modifier,
        buttonBgColor = settings.colorScheme.defaultButtonBgColor,
        fontColor = settings.colorScheme.fontColor,
        style = TextStyle(fontWeight = if(bold)FontWeight.ExtraBold else FontWeight.Normal),
        shape = settings.defaultShape,
        onClick = onClick
    )
    Spacer(Modifier.size(5.dp))
}
