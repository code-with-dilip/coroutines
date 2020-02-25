package com.learncoroutines.example

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class CoroutineVsThread {

    suspend fun coroutine_100000() {
        val jobs = List(100_000) {
            GlobalScope.launch {
                delay(1000L)
                print(".")
            }
        }
        jobs.forEach { it.join() }
    }

    fun thread_100000() {
        val jobs = List(100_000) {
            thread {
                Thread.sleep(1000L)
                print(".")
            }
        }
        jobs.forEach { it.join() }
    }
}

fun main(args: Array<String>) {

    runBlocking {
        CoroutineVsThread().coroutine_100000()
        //CoroutineVsThread().thread_100000()
    }
}