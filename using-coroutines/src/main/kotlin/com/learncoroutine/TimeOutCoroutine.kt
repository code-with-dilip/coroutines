package com.learncoroutine

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        //val job = withTimeout(100) {
        val job = withTimeoutOrNull(100) {
            repeat(1000) {
                yield()
                print(".")
                Thread.sleep(1)
            }
        }
        delay(100)

        if(job==null){
            println("timed out")
        }

    }
}