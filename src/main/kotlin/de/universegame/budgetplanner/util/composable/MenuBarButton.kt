package de.universegame.budgetplanner.util.composable

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private fun tabShape(rad: Dp) = GenericShape { size, direction ->
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
}

@Composable
fun MenuBarButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = modifier, shape = RoundedCornerShape(5.dp)) {
        Text(text)
    }
}
