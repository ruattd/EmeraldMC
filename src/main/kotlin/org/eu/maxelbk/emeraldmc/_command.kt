package org.eu.maxelbk.emeraldmc

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

var commandExecutor: CommandExecutor = EmeraldCommandExecutor
    @Deprecated("Overriding core components is not recommended.") set

private lateinit var commandMap0: MutableMap<String, EmeraldCommand>
val commandMap: Map<String, EmeraldCommand> get() = commandMap0

private fun registerCommand(command: EmeraldCommand) {
    commandMap0[command.name] = command
    server.commandMap.register(command.ownerId, object : Command(
        command.name, command.description, command.usageMessage, command.aliases
    ) {
        override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>)
        = commandExecutor.onCommand(sender, this, commandLabel, args)
    })
}

fun addCommand(command: EmeraldCommand): Boolean {
    val tmp = server.commandMap.getCommand(command.name)
    if (tmp != null) {
        logger.warning("Command '${command.fullName}' duplicates a existing command'")
        return false
    }
    registerCommand(command)
    return true
}

fun removeCommand(name: String): Boolean {
    val serverMap = server.commandMap
    val serverCommand = serverMap.getCommand(name)
    return (commandMap0.remove(name) != null) && (serverCommand?.unregister(serverMap) ?: false)
}

fun overrideCommand(newCommand: EmeraldCommand): Boolean {
    val name = newCommand.name
    val status = removeCommand(name)
    registerCommand(newCommand)
    if (status) logger.warning("Command '$name' is overridden by '${newCommand.fullName}'")
    return status
}

fun commandError(type: CommandErrorType) {
    throw CommandException(type)
}

data class CommandException(val type: CommandErrorType) : RuntimeException()
