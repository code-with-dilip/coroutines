package com.learncoroutine.context

import kotlinx.coroutines.*

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking {
    //outer coroutine
    val outer = launch {
        //inner coroutine
        try {
            launch(coroutineContext) {
                repeat(1000) {
                    println("$it")
                    delay(1)
                }
            }
           // delay(2000)
        } catch (ex: CancellationException) {
            //println()
            log("Cancellation Exception $ex")
        }

    }
    //outer.join()
   delay(200)
    //outer.cancelAndJoin()
    outer.cancelChildren()
    println()
    log("Finished")
    println("outer cancelled : ${outer.isCancelled}")
}