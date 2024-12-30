package dev.gathi.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

object KotlinCoroutineDispatcherProvider {

    val Dispatchers.LOOM get() = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()
}