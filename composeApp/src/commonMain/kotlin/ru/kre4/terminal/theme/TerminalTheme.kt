package ru.kre4.terminal.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TerminalTheme(content: @Composable () -> Unit) {
    val colors = darkGreen

//    CompositionLocalProvider(
//        LocalColors provides colors,
//        content = content
//    )

    MaterialTheme(
        colorScheme =
            darkColorScheme(
                primary = Color.White,
                secondary = Color.Black,
                tertiary = Color.Green,
                background = Color.Black,
                surface = Color.Black,
                primaryContainer = Color.Black
            ),
        typography = Typography(), // своя типографика
        content = content
    )
}

//object TerminalTheme {
//    val colors: ColorPalette
//        @Composable @ReadOnlyComposable
//        get() = LocalColors.current
//}
