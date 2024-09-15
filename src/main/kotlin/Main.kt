package dev.gathi

fun main() {
    println("Staring Platform thread vs. virtual thread")
    platformThreadExecutor()
    // virtual threads are daemon threads, so we need to wait for them to complete
    virtualThreadExecutor()
    Thread.sleep(2000)
}

fun platformThreadExecutor() {
    println("Platform Thread Executor")
    Thread.ofPlatform().start {
        println("Executing in Platform Thread ${Thread.currentThread()}")
        Thread.sleep(1000)
        println("Execution Completed in Platform Thread ${Thread.currentThread()}")
    }
}

fun virtualThreadExecutor() {
    println("Virtual Thread Executor")
    Thread.ofVirtual().start {
        println("Executing in Virtual Thread ${Thread.currentThread()}")
        Thread.sleep(1000)
        println("Execution Completed in Virtual Thread ${Thread.currentThread()}")
    }
}