package ru.kre4.terminal.commands

import androidx.compose.runtime.Composable
import ru.kre4.terminal.fs.EmulatedFileSystem

data class CommandResult(
    val content: (@Composable () -> Unit)? = null,
    val lines: List<CharSequence>? = null,
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

abstract class Command {
    fun executeCommand(args: List<String>, context: CommandExecutionContext): CommandResult {
        if (args.size == 1 && args[0] == "--help") {
            return CommandResult(lines = help())
        }

        return execute(args, context)
    }

    protected abstract fun execute(args: List<String>, context: CommandExecutionContext): CommandResult

    abstract fun name(): String

    abstract fun help(): List<CharSequence>
}
