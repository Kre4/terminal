package ru.kre4

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kre4.theme.TerminalTheme

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("") }
    TerminalTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize(0.8f)
                    .border(1.dp, Color.Green, RoundedCornerShape(10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Scaffold(
                    bottomBar = {
                        TextField(
                            value = text,
                            onValueChange = { newText -> text = newText },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            singleLine = true,
                            prefix = {Text(">")},
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                disabledBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                            )
                        )
                    }
                ) { paddingValues ->
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(paddingValues)
                            .fillMaxWidth()
                            .weight(5f)
                    ) {
                        Text("some text")
                    }
                }


            }
        }
    }
}