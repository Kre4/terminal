package ru.kre4.terminal.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kre4.terminal.TerminalLine
import ru.kre4.terminal.TerminalViewModel

@Composable
fun Terminal(viewModel: TerminalViewModel = TerminalViewModel()) {
    var input by remember { mutableStateOf("") }
//    var output by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { state.history.size }
            .collect { size ->
                if (size > 0) {
                    listState.scrollToItem(size - 1)
                }
            }
    }
    Column(
        modifier = Modifier
            .dropShadow(
                shape = RoundedCornerShape(12.dp),
                shadow = Shadow(
                    radius = 12.dp,
                    spread = 1.dp,
                    alpha = 0.7f,
                    color = Color.Green,
                    offset = DpOffset(x = 0.dp, 0.dp)
                )
            )
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(13.dp))
            .clip(RoundedCornerShape(12.dp))
            .fillMaxSize(0.8f)
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    listOf(Color.Green, Color.Green)
                ),
                shape = RoundedCornerShape(12.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Scaffold(
            modifier = Modifier.clip(RoundedCornerShape(14.dp))
                .background(Color.Transparent, RoundedCornerShape(1.dp)),
            bottomBar = {
                TextField(
                    value = input,
                    onValueChange = { newText -> input = newText },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = Color.Green,
                                start = Offset(x = 0f, y = 0f),
                                end = Offset(x = size.width, y = 0f),
                                strokeWidth = 1f
                            )
                        }
                        .onKeyEvent { event: KeyEvent ->
                            if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
//                                state. += "$ $SavedState.current.input\n"
                                viewModel.submitCommand(input)
                                input = ""
                                true
                            } else {
                                false
                            }
                        },
                    singleLine = true,
                    prefix = { Text(">") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    )

                )
            }
        ) { paddingValues: PaddingValues ->
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(paddingValues)
                    .fillMaxWidth(0.5f)
                    .weight(5f)
            ) {
//                Text(
//                    text = output,
//                    color = Color.White,
//                    modifier = Modifier
//                        .padding(
//                            start = 20.dp,
//                            top = 30.dp,
//                            end = 20.dp,
//                            bottom = 30.dp,
//                        )
//                        .verticalScroll(scrollState)
//
//                )
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            top = 30.dp,
                            end = 20.dp,
                            bottom = 30.dp,
                        ),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.history) { line ->
                        TerminalLineRenderer(line)
                    }
                }

            }
        }
    }
}

@Composable
fun TerminalLineRenderer(line: TerminalLine) {
    when (line) {
        is TerminalLine.Prompt -> Text(
            text = "> ${line.command}",
            color = Color.Green,
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
        )
        is TerminalLine.Output -> Text(
            text = line.text,
            style = line.style,
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
        )
        is TerminalLine.Error -> Text(
            text = line.message,
//            color = MaterialTheme.colorScheme.error,
            color = Color.Red,
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
        )
    }
}
