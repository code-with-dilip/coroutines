package com.learncoroutine

import com.learncoroutine.context.log
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

class CPS {

    suspend fun hello() = suspendCoroutine<String> {
        "Hello"
    }

    suspend fun retrieveItem(coroutineScope: CoroutineScope): String {
        val token = coroutineScope.async(Dispatchers.IO) { getToken() }
        val data = coroutineScope.async(Dispatchers.IO) {  invokeService(token.await()) }
        return data.await()
    }

    private suspend fun invokeService(token: String): String {
        delay(200)
        log("inside invokeService")
        return "Data"
    }

    private suspend fun getToken(): String {
        delay(200)
        log("inside getToken")
        return "token"
    }
}

fun main() {
val cps = CPS()
    runBlocking {
        val item = cps.retrieveItem(this)
        log("item : $item")
    }
}