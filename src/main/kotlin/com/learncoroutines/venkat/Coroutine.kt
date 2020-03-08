package com.learncoroutines.venkat

import kotlinx.coroutines.*
import java.util.concurrent.Executors

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

suspend fun taskc() {
    println("start taskc in Thread ${Thread.currentThread()}")
    yield()
    println("end taskc in Thread ${Thread.currentThread()}")
}


fun main() {
    println("start")
    runBlocking {
      //  launch { taska() }
        launch { taskb() }
        launch(Dispatchers.Default) { taska() }
        println("called taska and taskb from ${Thread.currentThread()}")

    }
    Executors.newSingleThreadExecutor().asCoroutineDispatcher().use { context -> //use function takes care of closing the executor
        runBlocking {
            launch(context) { taskc() }
        }

    }

    val noOfProcessors = Runtime.getRuntime().availableProcessors()
    Executors.newFixedThreadPool(noOfProcessors).asCoroutineDispatcher().use { context -> //use function takes care of closing the executor
        runBlocking {
            launch(context) { taskc() }
        }

    }
    println("done")
}