/**
 * <p>{@code emeraldmc.core} 是 EmeraldMC 的 Paper 插件集成与核心 API 模块。
 * <p>
 * 这个模块包含 EmeraldMC 插件的主体部分和其他常用的基于 Paper API 的
 * Kotlin 优化，以及对 Kotlin Script 的 DSL 支持。
 *
 * @since EmeraldMC 1.0
 */
module emeraldmc.core {
    exports org.eu.maxelbk.emeraldmc;
    exports org.eu.maxelbk.emeraldmc.dsl;

    requires org.bukkit;
    requires kotlin.stdlib;
    requires kotlin.stdlib.jdk7;
    requires java.scripting;
    requires java.logging;
    requires kotlinx.serialization.core;
    requires kotlinx.serialization.json;
}
