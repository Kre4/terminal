package ru.kre4.terminal.commands

import ru.kre4.terminal.fs.EmulatedFileSystem

class CdCommand : Command() {
    override fun execute(args: List<String>, context: CommandExecutionContext): CommandResult {
        require(args.size == 1)

        val cdResult = context.fileSystem.changeDirectory(args[0])

        return when (cdResult) {
            is EmulatedFileSystem.CdResult.Success -> CommandResult(lines = listOf())
            is EmulatedFileSystem.CdResult.Failure -> CommandResult(errorLines = listOf(cdResult.message))
        }

    }

    override fun name(): String = "cd"

    override fun help(): List<CharSequence> = listOf("Change directory. Usage: cd \"folderName\"")
}
