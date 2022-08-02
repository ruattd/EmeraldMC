package org.eu.maxelbk.emeraldmc.dsl

import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.eu.maxelbk.emeraldmc.*

fun commands(
    content: CommandHandlerScope.() -> Unit,
) {
    val scope = CommandHandlerScope()
    scope.content()
    //TODO
}

class CommandHandlerScope {
    /**
     * 新建指令，若已存在同名指令则使用脚本设置的 [id] 作为 fallback 前缀
     *
     * @param name 指令名，即 `/xxx` 中的 `xxx`
     * @param description 显示在指令信息中的描述文本
     * @param usageMessage 显示在提示信息和 help 内容中的指令用法
     * @param aliases 指令别名
     * @param onCommand 指令被执行的回调函数
     */
    fun new(
        name: String,
        description: String = "",
        usageMessage: String = "/$name",
        aliases: List<String> = listOf(),
        onCommand: (sender: CommandSender, parameter: CommandParameter, target: Entity?) -> Unit,
    ): Boolean {
        idNotNull
        return addCommand(EmeraldCommand(name, id!!, description, usageMessage, aliases, onCommand))
    }

    /**
     * 覆盖原有指令，若成功覆盖则控制台会输出警告信息，若原有指令不存在则同 [new]
     *
     * @param name 指令名，即 `/xxx` 中的 `xxx`
     * @param description 显示在指令信息中的描述文本
     * @param usageMessage 显示在提示信息和 help 内容中的指令用法
     * @param aliases 指令别名
     * @param onCommand 指令被执行的回调函数
     */
    fun override(
        name: String,
        description: String = "",
        usageMessage: String = "/$name",
        aliases: List<String> = listOf(),
        onCommand: (sender: CommandSender, parameter: CommandParameter, target: Entity?) -> Unit,
    ): Boolean {
        idNotNull
        return overrideCommand(EmeraldCommand(name, id!!, description, usageMessage, aliases, onCommand))
    }

    /**
     * 移除指令
     */
    fun remove(
        name: String
    )
    = removeCommand(name)
}
