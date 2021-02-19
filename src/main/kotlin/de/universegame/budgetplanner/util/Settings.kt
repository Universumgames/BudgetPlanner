package de.universegame.budgetplanner.util

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Seettings data struct for storing color schemes, json serializer, default storage filename, dateformats, etc.
 * */
data class Settings(
    val colorScheme: ColorScheme = ColorScheme(),
    val defaultShape: Shape = RoundedCornerShape(5.dp),
    val jsonSerializer: Json = Json {
        encodeDefaults = true
        prettyPrint = true
        ignoreUnknownKeys = true
    },
    val dataFileName: String = "./data.json",
    val importDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy", Locale.GERMAN)
)