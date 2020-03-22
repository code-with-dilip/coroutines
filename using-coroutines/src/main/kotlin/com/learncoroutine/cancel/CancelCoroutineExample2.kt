package com.learncoroutine.cancel

import kotlinx.coroutines.*
import java.lang.Thread.sleep

fun main() {
    runBlocking {
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            repeat(5 ) {
                sleep(100)
                //if(isActive) // this makes the coroutines to behave as cooperative
                  ensureActive() // this makes the coroutines to behave as cooperative
                        print("$it")
            }
        }
//        delay(500)
        delay(200)
        job.cancel()
        println("job cancelled")
    }
    sleep(1000)
}