package de.universegame.budgetplanner.util

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class Settings(
    val colorScheme: ColorScheme = ColorScheme(),
    val defaultShape: Shape = RoundedCornerShape(5.dp)
)