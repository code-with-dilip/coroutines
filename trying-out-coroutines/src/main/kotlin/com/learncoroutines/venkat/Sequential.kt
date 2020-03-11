package com.learncoroutines.venkat
import kotlinx.coroutines.runBlocking

fun task1(){
    println("start taska in Thread ${Thread.currentThread()}")
    println("end taska in Thread ${Thread.currentThread()}")
}

fun task2() {
    println("start task2 in Thread ${Thread.currentThread()}")
    println("end task2 in Thread ${Thread.currentThread()}")
}

fun main() {
    println("start")
    run {
        task1()
        task2()
        println("called task1 and task2 from ${Thread.currentThread()}")

    }
    println("done")
}