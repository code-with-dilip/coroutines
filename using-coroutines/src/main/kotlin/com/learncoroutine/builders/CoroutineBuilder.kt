package com.learncoroutine.builders

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    launch {
        delay(1000) // This does not block the thread. Releases the thread and have the thread available to run other coroutines
        println("WORLD")
        //coroutine_100000()
    }
    print("Hello, ")
    dowork()
}

suspend fun dowork() {

    delay(1500)

}
