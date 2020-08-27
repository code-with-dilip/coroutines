package com.learncoroutines.exceptions

import kotlinx.coroutines.*
import java.lang.RuntimeException

class ExceptionCoroutineLaunch {

    fun hello() {
        println("hello")
    }

    fun world() {
        // println("world")
        throw RuntimeException("Exception Occurred")
    }
}

fun main() {

    runBlocking {
        val scope = CoroutineScope(Job())
        scope.launch {
            delay(200)
            println("hello")
        }
        scope.launch {
            throw RuntimeException("Exception Occurred")
        }
        delay(3000)
        println("After Delay")
    }
}