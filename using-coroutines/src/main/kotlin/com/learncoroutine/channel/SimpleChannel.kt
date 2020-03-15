package com.learncoroutine.channel

import com.learncoroutine.context.log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val channel = Channel<Int>()
    launch {
        for ( x in 1..5){
            log("sending $x")
            channel.send(x)
        }
        log("Before closing the channel")
        channel.close()
    }
 /*   repeat(5){
        log("receive from channel ${channel.receive()}")
    }*/

    for (y in channel){
        log("receive from channel $y")
    }
}