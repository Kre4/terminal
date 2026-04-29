package ru.kre4.terminal.commands

class CommandRegistry {
    private val registry = mutableMapOf<String, Command>()

    init {
        register(
            HelpCommand()
        )
    }

    fun register(vararg commands: Command) {
        for (command in commands)
            registry[command.name().lowercase()] = command
    }

    fun resolve(name: String): Command? = registry[name.lowercase()]
}
