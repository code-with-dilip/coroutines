package com.learncoroutine.select

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

@ExperimentalCoroutinesApi
fun CoroutineScope.producer1() : ReceiveChannel<String> = produce {
    while (true){
        delay(100)
        send("from producer 1")
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.producer2() : ReceiveChannel<String> = produce<String> {
    while (true){
        delay(300)
        send("from producer 2")
    }
}

suspend fun selector(message1: ReceiveChannel<String>, message2: ReceiveChannel<String>){
    select<Unit> {
        message1.onReceive{value -> println(value)}
        message2.onReceive{value -> println(value)}

    }
}

fun main() = runBlocking {
    val m1 = producer1()
    val m2 = producer2()
    repeat(15){
        selector(m1,m2)
    }
}