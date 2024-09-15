package dev.gathi.utils

import kotlin.reflect.KClass

inline fun <reified T: KClass<out Throwable>> Throwable.handleMultiExceptions(vararg exceptionTypes: KClass<out Throwable>, block: (exp: Throwable) -> Unit) {
    for (exceptionType in exceptionTypes) {
        if (this.javaClass == exceptionType.java) {
            block(this)
            break
        }
    }
}