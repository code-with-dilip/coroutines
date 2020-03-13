package com.learncoroutine.async

import com.learncoroutine.context.log
import kotlinx.coroutines.*

suspend fun doWorkOne(): String {
    delay(300)
    log("Working 1")
    return "Hello"
}

suspend fun doWorkTwo(): String {
    delay(100)
    log("Working 2")
    return "World"
}


fun main() {

    val scope = CoroutineScope(Dispatchers.Default)
    val job = scope.launch {
        val r1 = async { doWorkOne() }

        val r2 = async { doWorkTwo() }
        log("result : ${r1.await() + r2.await()}")
    }
    runBlocking {
        job.join()
    }

}

