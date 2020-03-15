package com.learncoroutine.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
fun produceInfiniteNumbers() : ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {
    var x=1;
    while(true){
        send(x++)
    }
}

fun squareNumbers(numbers : ReceiveChannel<Int>) : ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {
    for(x in numbers){
        send(x*x)
    }
    println("done")
}

fun main() = runBlocking{

    val producer = produceInfiniteNumbers()
    val square = squareNumbers(producer)
    for (i in 1..5) println("square numbers are :  ${square.receive()}")

    square.cancel()
    producer.cancel()
}