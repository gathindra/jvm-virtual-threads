package dev.gathi.utils

import kotlinx.coroutines.delay
import java.lang.Thread.sleep

object ThreadUtils {

    fun Thread.randomThreadWait(task: String) {
        println("Executing $task in ${this.name}")
        val sleepTime = getSleepTime(task)
        sleep(sleepTime)
        println("Execution Completed $task in ${Thread.currentThread()}")
    }

    suspend fun randomWait(task: String) {
        println("Executing $task in ${Thread.currentThread()}")
        val sleepTime = getSleepTime(task)
        delay(sleepTime)
        println("Execution Completed $task in ${Thread.currentThread()}")
    }

    private fun getSleepTime(task: String): Long =
        (1000..5000).random().toLong()
            .also { println("Sleeping $task for $it ms") }
}