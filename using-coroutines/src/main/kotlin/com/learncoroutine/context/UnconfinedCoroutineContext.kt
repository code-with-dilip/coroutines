package com.learncoroutine.context

import kotlinx.coroutines.*

fun main() = runBlocking {

    val jobs = arrayListOf<Job>()
    jobs += launch(Dispatchers.Unconfined) {
        println("coroutineContext : I am working in thread [${Thread.currentThread().name}]")
        delay(100)
        //yield()
        println("coroutineContext : After Delay in thread [${Thread.currentThread().name}]")
    }

}
