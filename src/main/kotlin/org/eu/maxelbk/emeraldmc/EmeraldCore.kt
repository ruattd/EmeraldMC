package org.eu.maxelbk.emeraldmc

import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class EmeraldCore : JavaPlugin() {
    companion object {
        private var instance0: EmeraldCore? = null
        val instance: EmeraldCore? get() = instance0
    }

    override fun onEnable() {
        // Plugin startup logic
        instance0 = this
        logger.info("""
            ${ChatColor.BLUE}     [ Emerald MC Core ---------------------- PaperMC Plugin ]
            ${ChatColor.DARK_AQUA} ______                          _     _   __  __  _____ 
                                  |  ____|      version 1.0       | |   | | |  \/  |/ ____|
                                  | |__   _ __ ___   ___ _ __ __ _| | __| | | \  / | |     
                                  |  __| | '_ ` _ \ / _ \ '__/ _` | |/ _` | | |\/| | |     
                                  | |____| | | | | |  __/ | | (_| | | (_| | | |  | | |____ 
                                  |______|_| |_| |_|\___|_|  \__,_|_|\__,_| |_|  |_|\_____|
            
            ${ChatColor.BLUE}     [ Copyright (C) 2022 Maxel Black and other contributors ]
                                  [ ------ https://github.com/maxelblack/EmeraldMC ------ ]
        """.trimIndent())
        logger.info("\n")
        load()
    }

    override fun onDisable() {
        // Plugin shutdown logic
        save()
        instance0 = null
    }
}
