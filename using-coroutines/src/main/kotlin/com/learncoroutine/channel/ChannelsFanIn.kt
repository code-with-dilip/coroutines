package com.learncoroutine.channel

import com.learncoroutine.context.log
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


suspend fun produce(channel : Channel<String>, msg : String, interval: Long){
    while (true){
        delay(interval)
        channel.send(msg)
    }
}

fun main() = runBlocking<Unit> {
    val channel = Channel<String>()
    launch(coroutineContext) {
        produce(channel, "foo", 200L)
    }
    launch(coroutineContext) {
        produce(channel, "bar", 200L)
    }
    repeat(6){
        log(channel.receive())
    }
    coroutineContext.cancelChildren()
}