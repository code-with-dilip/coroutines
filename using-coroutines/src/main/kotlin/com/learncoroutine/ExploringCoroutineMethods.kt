package com.learncoroutine

import kotlinx.coroutines.*

fun main() {

    runBlocking {

        val job = launch {
            delay(1000)
            println("World")
        }
        print("Hello , ")
        job.join()

        val job1 = launch {
            repeat(1000) {
               if(!isActive) throw CancellationException()
                yield()
                //delay(100)
                print(".")
               // Thread.sleep(1)

            }
        }
        delay(10)
        job1.cancelAndJoin()
        println("done")
    }




}