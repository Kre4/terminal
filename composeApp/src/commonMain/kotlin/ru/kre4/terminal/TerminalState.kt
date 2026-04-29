package ru.kre4.terminal

import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kre4.terminal.commands.CommandRegistry

sealed interface TerminalLine {
    data class Prompt(val command: String) : TerminalLine
    data class Output(val text: String, val style: TextStyle = TextStyle.Default) : TerminalLine
    data class Error(val message: String) : TerminalLine
}

class TerminalViewModel : ViewModel() {
    private val registry = CommandRegistry()

    private val _state = MutableStateFlow(TerminalState())
    val state: StateFlow<TerminalState> = _state.asStateFlow()

    fun submitCommand(rawInput: String) {
        val trimmed = rawInput.trim()
        if (trimmed.isBlank()) return

        viewModelScope.launch {
            // 1. Фиксируем ввод пользователя
            appendLine(TerminalLine.Prompt(trimmed))

            val parts = trimmed.split(Regex("\\s+")).filter { it.isNotBlank() }
            val cmdName = parts.first()
            val args = parts.drop(1)

            // 2. Находим команду
            val cmd = registry.resolve(cmdName)
            if (cmd == null) {
                appendLine(TerminalLine.Error("Command not found: $cmdName"))
                return@launch
            }

            // 3. Выполняем в фоне
            try {
                val result = cmd.execute(args)
                if (result.isError) {
                    result.lines.forEach { appendLine(TerminalLine.Error(it)) }
                } else {
                    result.lines.forEach { appendLine(TerminalLine.Output(it)) }
                }
            } catch (e: Exception) {
                appendLine(TerminalLine.Error("Unexpected error: ${e.message}"))
            }
        }
    }

    private fun appendLine(line: TerminalLine) {
        _state.update { it.copy(history = it.history + line) }
    }

    // Состояние терминала
    data class TerminalState(
        val history: List<TerminalLine> = emptyList(),
        val isProcessing: Boolean = false
    )
}
