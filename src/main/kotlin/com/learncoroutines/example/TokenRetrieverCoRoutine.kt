package com.learncoroutines.example

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KLogging

class TokenRetrieverCoRoutine {
    companion object : KLogging()

    suspend fun retrieveToken(): String {
        return "token"
    }

    suspend fun externalCall(token: String): String {
        logger.info("Invoked the external servie")
        return "Success"
    }

    suspend fun invokeService() {
        val token = retrieveToken()
        val result = this.externalCall(token)
        logger.info("Result is $result")
    }
}

fun invokeCorouteInaLoop(){
    (0..9).forEach {
        GlobalScope.launch {
            TokenRetrieverCoRoutine().invokeService()
        }
    }
}

fun  invokeCoroutine(){
   val job =  GlobalScope.launch {
        TokenRetrieverCoRoutine().invokeService()
    }
    job.invokeOnCompletion { println("job completed") }
}
fun main(args: Array<String>) {
    invokeCoroutine()
  //  invokeCorouteInaLoop()
    Thread.sleep(3000)
}