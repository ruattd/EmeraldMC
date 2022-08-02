package org.eu.maxelbk.emeraldmc.dsl

import kotlinx.serialization.Serializable
import org.eu.maxelbk.emeraldmc.coreConfig

private var accessible = true

internal fun endEmeraldConfigAccessible() {
    accessible = false
}

fun emeraldConfig(
    content: EmeraldConfigScope.() -> Unit
) {
    if (accessible) {
        val scope = EmeraldConfigScope()
        scope.content()
        coreConfig = scope
        endEmeraldConfigAccessible()
    } else {
        coreLogger.warning("emeraldConfig: Duplicate configuration rejected ($id)")
    }
}

/**
 * @param enable 启用插件，仅调试用
 * @param allowScripts 允许加载 Kotlin 脚本，若不允许此项则必须使用 `_config.json` 作为 EmeraldMC 的配置文件
 * @param allowUserScripts 允许加载自定义 Kotlin 脚本（即非下划线开头的脚本），该项需要 [allowScripts] 设置为 `true`
 * @param allowOnlineDependency **该配置项已失效** 允许加载在线依赖文件（即解析 Maven 库等）
 * @param enableVerification 启用加入世界后的密码验证，需要配置密码处理
 * @param everyoneIsOperator 让所(xiong)有(hai)人(zhi)全部变成服务器管理员！
 * @param motd 显示在服务器列表中服务器名下方的简介文本，支持格式化文本，不建议使用换行
 */
@Serializable
data class EmeraldConfigScope(
    var enable: Boolean = true,
    var allowScripts: Boolean = true,
    var allowUserScripts: Boolean = false,
    var enableVerification: Boolean = false,
    var everyoneIsOperator: Boolean = false,
    var motd: String = "Please ask the server owner to set MOTD.",
    @Deprecated("This configuration item has expired")
    var allowOnlineDependency: Boolean = true,
)
