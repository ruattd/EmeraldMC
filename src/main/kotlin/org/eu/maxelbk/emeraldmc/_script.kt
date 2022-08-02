package org.eu.maxelbk.emeraldmc

import org.eu.maxelbk.emeraldmc.dsl.*
import java.nio.file.Path
import javax.script.ScriptEngineManager
import kotlin.io.path.name

internal fun runScript(path: Path) {
    if (coreConfig.allowScripts) {
        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
        readFile(path) { scriptEngine.eval(it) }
    } else {
        logger.warning("script: Scripts is disallowed (${path.name})")
    }
}

internal fun runUserScript(path: Path) {
    if (coreConfig.allowUserScripts) {
        runScript(path)
    } else {
        logger.warning("script: User scripts is disallowed (${path.name})")
    }
}

internal fun loadScripts() = file(dataFolder) {

    scriptFiles.forEach {
        TODO()
    }
}
