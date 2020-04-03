package com.learncoroutine.cancel

import kotlinx.coroutines.*

fun main() {

    runBlocking {
        val job1 = launch {
            try {
                repeat(10) {
                    delay(200)
                    //if(!isActive) throw CancellationException()
                    yield()
                    //delay(100)
                    print(".")
                    // Thread.sleep(1)

                }
            } catch (ex: CancellationException) {
                println("Cancellation exception : ${ex}")
            } finally {
                run {
                    println("Close Resources if any")
                }
            }
        }


        delay(500)
        job1.cancel()
        println("done")
    }
}