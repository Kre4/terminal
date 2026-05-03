package ru.kre4

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.kre4.terminal.theme.Terminal
import ru.kre4.terminal.theme.TerminalTheme
import ru.kre4.terminal.theme.animaton.SvgFallAnimation

@Composable
@Preview
fun App() {
    var windowOffsetX by remember { mutableStateOf(0f) }
    var windowOffsetY by remember { mutableStateOf(0f) }

    TerminalTheme {
        Box(
            Modifier
                .background(Color(0xFF111111))
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .offset(x = windowOffsetX.dp, y = windowOffsetY.dp)
                    .zIndex(200f)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            windowOffsetX += dragAmount.x
                            windowOffsetY += dragAmount.y
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Terminal()
            }

            SvgFallAnimation()

        }
    }
}
