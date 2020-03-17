package com.learncoroutine.experiment

import com.learncoroutine.context.log
import kotlinx.coroutines.*
import java.lang.Thread.sleep

suspend fun task1Service(): String {
    delay(1000)
    return "Hello"
}

suspend fun task1(): String {
    log("task1")
    val job = withContext(Dispatchers.IO) {
        async { task1Service() }
    }
    return job.await()
}

suspend fun task2() {
    delay(200)
    log("task2")
}

fun main() = runBlocking {
    //val scope = CoroutineScope(Dispatchers.IO)
    launch(Dispatchers.IO) {
        val str = task1()
        log("response is : $str")
    }
    launch(Dispatchers.IO) { task2() }

    Thread.sleep(2000)

}