package ru.kre4.terminal.commands

class HelpCommand: Command {
    override fun execute(args: List<String>): CommandResult {
        return CommandResult(
            lines = listOf("no help here buddy")
        )
    }

    override fun name(): String = "help"
}
