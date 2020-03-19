package com.learncoroutine.context

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val job = launch {
        println("isActive ? : ${coroutineContext[Job]!!.isActive}")
        println("coroutineContext : $coroutineContext")
    }
    job.join()

    println("isActive ? : ${job.isActive}")

}