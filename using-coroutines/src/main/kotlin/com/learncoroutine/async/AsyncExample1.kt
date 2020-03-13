package com.learncoroutine.async

import com.learncoroutine.context.log
import kotlinx.coroutines.*

suspend fun doWork(msg: String): String {
    log("Working $msg ")
    delay(300)
    log("Working $msg")
    return "Hello"
}


fun main() {

    runBlocking {
        val job = launch {
            val r1 = async { doWork("First Job") }
        }
        doWork("Second Job")
        job.join()
    }

}

