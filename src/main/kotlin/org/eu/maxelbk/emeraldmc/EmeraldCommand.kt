package org.eu.maxelbk.emeraldmc

import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity


data class EmeraldCommand(
    val name: String,
    val ownerId: String,
    val description: String = "",
    val usageMessage: String = "/$name",
    val aliases: List<String> = listOf(),
    val onCommand: (
        sender: CommandSender,
        parameter: CommandParameter,
        target: Entity?,
    ) -> Unit,
) {
    val fullName get() = "$ownerId:$name"
}
