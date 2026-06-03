package ru.kre4.terminal.commands

import androidx.compose.runtime.Composable
import ru.kre4.terminal.fs.EmulatedFileSystem

data class CommandResult(
    val content: (@Composable () -> Unit)? = null,
    val lines: List<String>? = null,
    val errorLines: List<String>? = null,
    val exitCode: Int = 0
) {
    init {
        require(content != null || lines != null || errorLines != null)
    }
}

data class CommandExecutionContext(
    val fileSystem: EmulatedFileSystem
)

interface Command {
    fun execute(args: List<String>, context: CommandExecutionContext): CommandResult

    fun name(): String
}
