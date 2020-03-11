package com.learncoroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun launching_100000_threads() {
    println("Started thread")
    val startTime = System.currentTimeMillis()
    val result = AtomicInteger()
    val threads = List(100_000) {
        thread {
            sleep(1000L)
            result.getAndIncrement()
        }
    }
    threads.forEach { it.join() }
    println("launching_100000_threads : $result  and the total time is ${System.currentTimeMillis()-startTime}ms")
}

suspend fun coroutine_100000() {
    val startTime = System.currentTimeMillis()
    println("Started Coroutine")
    val result = AtomicInteger()
    val jobs = List(100_000) {
        GlobalScope.launch {
            delay(1000L)
            result.getAndIncrement()
        }
    }
    jobs.forEach { it.join() }
    println("coroutine_100000 : $result and the total time is ${System.currentTimeMillis()-startTime}ms" )

}

fun main() {

    GlobalScope.launch {
        delay(1000) // This does not block the thread. Releases the thread and have the thread available to run other coroutines
        println("WORLD")
        //coroutine_100000()
    }
    print("Hello, ")

    sleep(1500)
    //launching_100000_threads()

}