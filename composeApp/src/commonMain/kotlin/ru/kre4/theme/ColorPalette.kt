package ru.kre4.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorPalette(
    val mainColor: Color,
    val singleTheme: Color,
    val oppositeTheme: Color,
)

val darkGreen = ColorPalette(
    mainColor = Color.Green, // просто цвет из Color.kt
    singleTheme = Color.White,
    oppositeTheme = Color.Black,
)

val LocalColors = staticCompositionLocalOf<ColorPalette> {
    darkGreen
}
