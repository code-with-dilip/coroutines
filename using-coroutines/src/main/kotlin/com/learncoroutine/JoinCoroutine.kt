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

    }
}
