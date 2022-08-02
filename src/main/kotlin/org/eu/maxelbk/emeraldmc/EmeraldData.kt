package org.eu.maxelbk.emeraldmc

data class EmeraldData(
    val originData: String
) {
    companion object {
        private val handlerMap0 = mutableMapOf<Class<*>, (String) -> Any>()
        val handlerMap: Map<Class<*>, (String) -> Any> get() = handlerMap0

        fun putHandler(type: Class<*>, onHandle: (String) -> Any) {
            handlerMap0[type] = onHandle
        }

        fun removeHandler(type: Class<*>) {
            handlerMap0.remove(type)
        }
    }

    inline fun <reified T> convert(): T {
        val type = T::class.java
        val onHandle = handlerMap[type]
        return type.cast(onHandle?.let { it(originData) }
            ?: commandError(CommandErrorType.PARAMETER_UNSUPPORTED_TYPE))
    }
}
