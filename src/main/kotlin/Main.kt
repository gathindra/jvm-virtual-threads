package dev.gathi


import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

fun main() {
    println("Staring Platform thread vs. virtual thread")
    platformThreadExecutor()

    virtualThreadExecutor()
    Thread.sleep(4000)
}

fun platformThreadExecutor() {
    println("Platform Thread Executor")
    Thread.ofPlatform().start {
        println("Executing in Platform Thread ${Thread.currentThread()}")
        Thread.sleep(1000)
        println("Execution Completed in Platform Thread ${Thread.currentThread()}")
    }

    Executors.newFixedThreadPool(4).use { executor ->
        (1..100).forEach { index ->
            executor.submit {
                println("Executing in Platform Thread $index ${Thread.currentThread()}")
                Thread.sleep(100)
                println("Execution Completed in Platform $index Thread ${Thread.currentThread()}")
            }
        }
    }
}

fun virtualThreadExecutor() {
    println("Virtual Thread Executor")
    // One way to create a virtual thread
     Thread.ofVirtual().start {
        println("Executing in Virtual Thread 1 ${Thread.currentThread()}")
        Thread.sleep(2000)
        println("Execution Completed in Virtual 1 Thread ${Thread.currentThread()}")
    }

    // Another way to create a virtual thread
    Thread.startVirtualThread {
        println("Executing in Virtual Thread 2 ${Thread.currentThread()}")
        Thread.sleep(1000)
        println("Execution Completed in Virtual 2 Thread ${Thread.currentThread()}")
    }

    // Using Executors to create virtual threads
    Executors.newVirtualThreadPerTaskExecutor().use { executor ->
        (1..100).forEach { index ->
            executor.submit {
                println("Executing in Virtual Thread $index ${Thread.currentThread()}")
                Thread.sleep(100)
                println("Execution Completed in Virtual $index Thread ${Thread.currentThread()}")
            }
        }
    }
}
