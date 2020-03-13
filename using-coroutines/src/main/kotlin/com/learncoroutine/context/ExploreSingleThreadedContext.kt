package com.learncoroutine.context

import kotlinx.coroutines.*
import java.util.concurrent.Executors

fun main() {
    Executors.newSingleThreadExecutor().asCoroutineDispatcher().use { context -> //use function takes care of closing the executor
        runBlocking {
            launch(CoroutineName("singlethreadedcontext")+context) { log("singlethreadexecutor") }
        }


    }



}