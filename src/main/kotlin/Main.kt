package dev.gathi


import java.util.concurrent.Executors

fun main() {
    println("Staring Platform thread vs. virtual thread")

    startPlatformThread()
//    startVirtualThread()
//    platformThreadExecutor()
//    virtualThreadExecutor()

}

fun startPlatformThread() {
    println("Starting Platform Thread")
    Thread.ofPlatform().start {
        println("Executing in Platform Thread ${Thread.currentThread()}")
        Thread.sleep(1000)
        println("Execution Completed in Platform Thread ${Thread.currentThread()}")
    }

}

fun startVirtualThread() {
    println("Starting Virtual Threads")
    // One way to create a virtual thread
    Thread.ofVirtual().start {
        println("Executing in Virtual Thread 1 ${Thread.currentThread()}")
        Thread.sleep(1000)
        println("Execution Completed in Virtual 1 Thread ${Thread.currentThread()}")
    }

    // Another way to create a virtual thread
    Thread.startVirtualThread {
        println("Executing in Virtual Thread 2 ${Thread.currentThread()}")
        Thread.sleep(1000)
        println("Execution Completed in Virtual 2 Thread ${Thread.currentThread()}")
    }
}

fun platformThreadExecutor() {
    println("Platform Thread Executor")
    // Using Executors to create a fixed thread pool
    Executors.newFixedThreadPool(8).use { executor ->
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
    // Using Executors to create virtual threads
    // No thread pool, each task gets its own virtual thread
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
