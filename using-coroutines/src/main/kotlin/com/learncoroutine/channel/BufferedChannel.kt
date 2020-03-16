package com.learncoroutine.channel

import com.learncoroutine.context.log
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = Channel<Int>(3) // channel buffer of size 3
    val sender = launch(coroutineContext) {
        repeat(10) {
            log("sending $it")
            channel.send(it)
        }
        channel.close()
    }
    delay(1000)
    for (y in channel){
        log("receive from channel $y")
    }
    log("cancel complete")
}