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

fun main(args : Array<String>) {
    GlobalScope.launch {
        TokenRetrieverCoRoutine().invokeService()
    }
    Thread.sleep(3000)
}