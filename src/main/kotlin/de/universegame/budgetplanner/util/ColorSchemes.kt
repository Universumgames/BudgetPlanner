package de.universegame.budgetplanner.util

import androidx.compose.ui.graphics.Color

interface SimpleColorScheme {
    val fontColor: Color
}

data class BalanceListColors(
    override val fontColor: Color = Color.White,
    val positiveEntryFontColor: Color = Color.Green,
    val negativeEntryFontColor: Color = Color.Red,
    val yearOverviewBgColor: Color = Color(0xff2d2d2d),
    val monthOverviewBgColor: Color = Color(0xff202020),
    val oneTimeEntryBgColor: Color = Color(0xff1d1d1d),
) : SimpleColorScheme

data class ColorScheme(
    val balanceListColors: BalanceListColors = BalanceListColors(),
    val appBackground: Color = Color(0xff1d1d1d),
    override val fontColor: Color = Color.White,
    val defaultButtonBgColor: Color = Color(0xff5d5d5d),
    val widgetBgColor: Color = Color(0xff202020)
) : SimpleColorScheme