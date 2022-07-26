package org.eu.maxelbk.emeraldmc

import org.bukkit.command.Command
import org.bukkit.plugin.java.JavaPlugin

class EmeraldCore : JavaPlugin() {
    override fun onEnable() {
        load()
        // Plugin startup logic
    }

    override fun onDisable() {
        save()
        // Plugin shutdown logic
    }
}
