package com.learncoroutines.venkat

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

suspend fun taska() {
    println("start taska in Thread ${Thread.currentThread()}")
    yield()
    println("end taska in Thread ${Thread.currentThread()}")
}

suspend fun taskb() {
    println("start taskb in Thread ${Thread.currentThread()}")
    yield()
    println("end taskb in Thread ${Thread.currentThread()}")
}

fun main() {
    println("start")
    runBlocking {
        launch { taska() }
        launch { taskb() }
        println("called taska and taskb from ${Thread.currentThread()}")

    }
    println("done")
}