package com.learncoroutine

import kotlinx.coroutines.*

fun main() {


    Thread.sleep(1000)
    runBlocking {

        val job = launch {
            delay(1000)
            println("World")
        }
        print("Hello , ")
        job.join()

        val job1 = launch {
            try {
                repeat(1000) {
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


        delay(10)
        job1.cancelAndJoin()
        println("done")
    }
}
