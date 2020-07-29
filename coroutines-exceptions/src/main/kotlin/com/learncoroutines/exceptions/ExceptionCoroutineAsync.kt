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

    suspend fun worldScope() : String = coroutineScope {
        throw RuntimeException("Exception Occurred")
    }
}

fun main() {

    runBlocking {
        val exceptionCoroutine = ExceptionCoroutineAsync()
        val scope = CoroutineScope(Job())
        val hello = scope.async { exceptionCoroutine.hello() }
        val world = scope.async { exceptionCoroutine.world() }
        print(world.await()) // This throws the exception
        print(hello.await())
    }
}