package ru.kre4.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun Terminal() {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .dropShadow(
                shape = RoundedCornerShape(12.dp),
                shadow = androidx.compose.ui.graphics.shadow.Shadow(
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
                        .onKeyEvent { event: KeyEvent ->
                            if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                                output += "$ $input\n"
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
                Text(
                    text = output,
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 30.dp,
                        end = 20.dp,
                        bottom = 30.dp,
                    )
                )
            }
        }
    }
}
