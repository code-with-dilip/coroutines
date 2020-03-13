package com.learncoroutine.async

import com.learncoroutine.context.log
import kotlinx.coroutines.*


suspend fun doWorkLazy(msg: String): String {
    log("Working $msg ")
    delay(200)
    log("Completed Work $msg")
    return "Hello"
}


fun main() = runBlocking {

    val job = launch {
        val result = async(start = CoroutineStart.LAZY) {
            doWorkLazy("Hello")
        }
        result.await() // This executes the coroutine
    }
    job.join()
    println("Completed")
}

