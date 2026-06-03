package ru.kre4.terminal.commands

class PwdCommand : Command  {
    override fun execute(args: List<String>, executionContext: CommandExecutionContext): CommandResult {
        var currentPath = executionContext.fileSystem.currentPath()
        return CommandResult(lines = listOf(currentPath))
    }

    override fun name(): String = "ls"
}
