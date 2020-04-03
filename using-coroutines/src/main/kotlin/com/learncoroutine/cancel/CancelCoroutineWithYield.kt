package com.learncoroutine.cancel

import kotlinx.coroutines.*
import java.lang.Thread.sleep

fun main() {
    runBlocking {
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            repeat(5) {
                delay(100)
                yield()
                print("$it")
            }
        }
//        delay(500)
        delay(300)
        job.cancel()
        println("job cancelled")
    }
    sleep(1000)
}