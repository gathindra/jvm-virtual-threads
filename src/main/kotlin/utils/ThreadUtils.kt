package dev.gathi.utils

object ThreadUtils {

    fun Thread.randomWait(task: String) {
        println("Executing $task in ${Thread.currentThread()}")
        val sleepTime = (1000..5000).random().toLong().also { println("Sleeping $task for $it ms") }
        Thread.sleep(sleepTime)
        println("Execution Completed $task in ${Thread.currentThread()}")
    }
}