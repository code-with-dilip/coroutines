package com.learncoroutines.exceptions

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.lang.RuntimeException

fun hello(): String {
    return "Hello"
}

fun world(): String {
    throw RuntimeException("Exception Occurred")
}

suspend fun main() = coroutineScope {

    val helloAsync = async { hello() }
    val worldAsync = async { world() }
    delay(500)

    println("${helloAsync.await()}")
    println("after delay")
    println("${worldAsync.await()}")

}