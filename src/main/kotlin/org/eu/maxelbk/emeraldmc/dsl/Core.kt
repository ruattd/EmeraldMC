package org.eu.maxelbk.emeraldmc.dsl

import org.bukkit.Bukkit
import org.eu.maxelbk.emeraldmc.runScript
import java.nio.file.Path
import kotlin.io.path.name

val logger get() = Bukkit.getLogger()

internal val coreLogger get() = org.eu.maxelbk.emeraldmc.logger

val server get() = Bukkit.getServer()

private lateinit var loadingInfo: ScriptInfo

private var loadingScriptId: String? = null

private var loadingScriptUri: Path? = null

@Synchronized
internal fun loadScript(uri: Path) {
    loadingScriptUri = uri
    runScript(uri)
    loadingScriptId = null
    loadingScriptUri = null
}

/**
 * 正在执行的脚本对应的标识符，务必在脚本的第一行设置id
 */
var id: String?
    get() = loadingScriptId
    set(value) {
        if (loadingScriptId == null) {
            loadingScriptId = value
        } else {
            coreLogger.warning("id: Duplicate setting rejected")
        }
    }

internal val idNotNull get() = idNotNull()
internal fun idNotNull(content: () -> Unit = {}) {
    if (id == null) {
        throw NullPointerException("ID is not set (${loadingScriptUri!!.name})")
    } else {
        content()
    }
}

internal data class ScriptInfo(
    val name: String,
    val id: String,
    val author: Array<String> = arrayOf(""),
    val version: Int = 0,
    val versionName: String? = null,
    val description: String = "",
) {
    /* hashCode & equals by IDEA auto generation */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScriptInfo

        if (name != other.name) return false
        if (id != other.id) return false
        if (!author.contentEquals(other.author)) return false
        if (version != other.version) return false
        if (versionName != other.versionName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + author.contentHashCode()
        result = 31 * result + version
        result = 31 * result + versionName.hashCode()
        return result
    }
}

/**
 * 脚本信息配置
 */
fun info(
    content: ScriptInfoScope.() -> Unit,
) {
    val scope = ScriptInfoScope()
    scope.content()
    loadingInfo = scope.getScriptInfo()
}

class ScriptInfoScope {
    /**
     * 显示在脚本信息中的名称
     */
    lateinit var name: String

    /**
     * 显示在脚本信息中的作者
     */
    var author = arrayOf("")

    /**
     * 脚本当前版本号，用于版本升级验证，设置为 `0` 以禁用验证
     */
    var version = 0

    /**
     * 显示在脚本信息中的版本名
     */
    var versionName = ""

    /**
     * 显示在脚本信息中的描述
     */
    var description = ""

    internal fun getScriptInfo(): ScriptInfo {
        idNotNull
        return ScriptInfo(name, loadingScriptId!!, author, version, versionName, description)
    }
}
