package ru.kre4.terminal.commands

data class CommandResult(
    val lines: List<String> = emptyList(),
    val isError: Boolean = false,
    val exitCode: Int = 0
)

interface Command {
    fun execute(args: List<String>): CommandResult

    fun name(): String
}
