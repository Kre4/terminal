package ru.kre4.terminal.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorPalette(
    val mainColor: Color,
    val mainBrightColor: Color,
    val mainDarkColor: Color,
    val attentionColor: Color
)

val DarkGreenPalette = ColorPalette(
    mainColor = Color(0xAA22c55e), // border, lines etc
    mainBrightColor = Color(0xff05df72), // input
    mainDarkColor = Color(0xB300c950), // output Color(0xff22c55e)
    attentionColor = Color(0xFFff6467)
)
