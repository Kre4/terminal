package ru.kre4.terminal.theme

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.kre4.terminal.TerminalLine

@Composable
fun TerminalLineRenderer(line: TerminalLine) {
    when (line) {
        is TerminalLine.Prompt -> Text(
            text = "> ${line.command}",
            color = DarkGreenPalette.mainBrightColor,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight(800),
            fontSize = 14.sp
        )
        is TerminalLine.Output -> when (val text = line.text) {
            is AnnotatedString -> Text(
                text = text,
                color = DarkGreenPalette.mainDarkColor,
                style = line.style,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight(800),
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
            else -> Text(
                text = text.toString(),
                color = DarkGreenPalette.mainDarkColor,
                style = line.style,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight(800),
                fontSize = 14.sp,
                lineHeight = 21.sp,
            )
        }
        is TerminalLine.Error -> Text(
            text = line.message,
            color = DarkGreenPalette.attentionColor,
            fontWeight = FontWeight(800),
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp
        )
        is TerminalLine.Composable -> line.content
    }
}
