package com.learncoroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class CoroutineExceptionExample {
}

fun main() {

    val scope = CoroutineScope(Job())
    val job = scope.launch(start = CoroutineStart.LAZY) {
        println("Inside Launch")
    }
    println(" ${job.isActive} , isCompleted : ${job.isCompleted}")
    job.start()
    sleep(1000)
    println(" ${job.isActive} , isCompleted : ${job.isCompleted}")


}