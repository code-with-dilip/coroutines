package com.learncoroutine.async

import com.learncoroutine.context.log
import kotlinx.coroutines.*

fun main() {
    val result = doWorkAsync("Hello")
    runBlocking {
        result.await()
    }
}

fun doWorkAsync(msg: String)  : Deferred<String> =  CoroutineScope(Dispatchers.IO).async {
    log("Started Working $msg ")
    delay(300)
    log("Completed Work $msg")
    return@async "Hello"

}
