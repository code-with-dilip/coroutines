package com.learncoroutine.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


@ExperimentalCoroutinesApi
fun main() = runBlocking{
    val channel = produceNumbers()
    channel.consumeEach {
        println("Receiving : $it")
    }
}

@ExperimentalCoroutinesApi
fun produceNumbers() : ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {
    for(x in 1..5){
        println("Sending : $x")
        send(x)
    }
    println("done")
}
