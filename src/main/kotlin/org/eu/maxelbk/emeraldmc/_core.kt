package org.eu.maxelbk.emeraldmc

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.eu.maxelbk.emeraldmc.dsl.*
import java.nio.file.Path
import java.util.jar.JarFile
import kotlin.io.path.name
import kotlin.io.path.readText

// --- basic components ---

internal val instance get() = EmeraldCore.instance!!
internal val logger get() = instance.logger
internal val server get() = instance.server
internal val pluginConfig get() = instance.config
internal val dataFolder: Path get() = instance.dataFolder.toPath()

// --- core config value ---

private var isCoreConfigModified = false
private lateinit var coreConfig0: EmeraldConfigScope

internal var coreConfig: EmeraldConfigScope
    get() = coreConfig0
    set(value) {
        if (!isCoreConfigModified) {
            coreConfig0 = value
            isCoreConfigModified = true
        }
    }

// --- feature functions ---

internal fun save() {
    //TODO
}

internal fun load() {
    loadConfig()
    loadScripts()
    //TODO
}

// --- config loading ---

internal val scriptFiles = arrayListOf<Path>()
internal val configFiles = mutableMapOf<String, Path>()

private fun loadConfig() = file(dataFolder) {
    if (notExists) {
        createDirectory()
        copyConfigScript()
    }
    val systemFiles = mutableMapOf<String, Path>()
    list().forEach {
        val fileName = it.name
        // Starts with underline means system files
        if (fileName.startsWith('_')) systemFiles[fileName] = it
        // Other runnable scripts
        else if (fileName.endsWith(".kts")) scriptFiles.add(it)
    }
    if (systemFiles.isNotEmpty()) {
        val configJsonFile = systemFiles["_config.json"]
        if (configJsonFile == null) {
            systemFiles["_config.kts"]?.let {
                runScript(it)
                endEmeraldConfigAccessible()
            }
        } else {
            coreConfig = Json.decodeFromString(configJsonFile.readText())
            endEmeraldConfigAccessible()
        }
    }
    else copyConfigScript()
}

private fun copyConfigScript() {
    logger.info("Config file not found! Creating config script...")
    val filepath = EmeraldCore::class.java.protectionDomain.codeSource.location.file
    val jarFile = JarFile(filepath)
    val input = jarFile.getInputStream(jarFile.getJarEntry("scripts/_config.kts"))
    file(dataFolder.resolve("_config.kts")) {
        val output = newOutputStream()
        var tmp: Int = input.read()
        while (tmp  != -1) {
            output.write(tmp)
            tmp = input.read()
        }
    }
}
