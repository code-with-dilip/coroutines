package com.learncoroutine.custom_suspendfunctions

import com.learncoroutine.context.log
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

suspend fun task1(){
    delay(100)
    return suspendCancellableCoroutine {
        log("inside task 1")
        it.invokeOnCancellation {
            log("Inside Cancellation")
        }
    }
}

suspend fun task2(){
    delay(100)
        log("inside task 1")
}

fun main() {

    runBlocking {
        val job = launch {
            //task1()
            task2()
        }

        val suspendCancellationJob = launch {
            task1()
        }
        delay(1000)
        job.join()
        suspendCancellationJob.cancelAndJoin()
    }
}