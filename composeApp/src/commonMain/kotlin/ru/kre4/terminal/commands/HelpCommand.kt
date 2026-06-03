package ru.kre4.terminal.commands

class HelpCommand: Command {
    override fun execute(args: List<String>, executionContext: CommandExecutionContext): CommandResult {
        return CommandResult(
            lines = listOf(
                "Available commands:",
                "help    - you just tried that"
            )
        )
    }

    override fun name(): String = "help"
}
