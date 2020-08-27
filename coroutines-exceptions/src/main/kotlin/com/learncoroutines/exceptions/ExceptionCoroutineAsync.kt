package com.learncoroutines.exceptions

import kotlinx.coroutines.*
import java.lang.RuntimeException

class ExceptionCoroutineAsync {

    fun hello(): String {
        return "Hello"
    }

    fun world(): String {
        throw RuntimeException("Exception Occurred")
    }
}

fun main() {

    runBlocking {
        val scope = CoroutineScope(Job())
        val helloAsync = scope.async {
            "Hello"
        }
        val worldAsync: Deferred<String> = scope.async {
            throw RuntimeException("Exception Occurred")
        }
        val worldAsync1: Deferred<String> = scope.async {
            "world1"
        }
        print(helloAsync.await()) // This throws the exception
        delay(3000)
        println("After Delay")
        println(worldAsync.await()) // This throws the exception
        println(worldAsync1.await()) // This throws the exception
    }
}