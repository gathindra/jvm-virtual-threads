package dev.gathi.utils

import kotlinx.coroutines.delay
import java.lang.Thread.sleep

object ThreadUtils {

    fun Thread.randomThreadWait(task: String, waitTime: Long? = null) {
        println("Executing $task in ${Thread.currentThread()}")
        val sleepTime = waitTime ?: getSleepTime(task)
        println("Sleeping $task for $sleepTime ms")
        sleep(sleepTime)
        println("Waiting for result Completed $task in ${Thread.currentThread()}")
    }

    suspend fun randomWait(task: String, waitTime: Long? = null) {
        println("Executing $task in ${Thread.currentThread()}")
        val sleepTime = waitTime ?: getSleepTime(task)
        println("Sleeping $task for $sleepTime ms")
        delay(sleepTime)
        println("Waiting for result Completed $task in ${Thread.currentThread()}")
    }

    private fun getSleepTime(task: String): Long = (500..2000).random().toLong()
}