package com.learncoroutine.cancel

import kotlinx.coroutines.*

fun main() {

    runBlocking {
        val job1 = launch {
            try {
                repeat(10) {
                    delay(200)
                    yield()
                    print("$it")
                }
            } catch (ex: CancellationException) {
                println("Cancellation exception : ${ex}")
            } finally {
                println("Close Resources if any")
                //this is a special suspending function which takes care changing the context first
                // Noncancellable is a check with is always active irrespective of the coroutine was cancelled.
                withContext(NonCancellable){
                    delay(100)
                    println("Close Resources if any after delay")
                }
            }
        }
        delay(500)
        job1.cancel()
        println("done")
    }
}