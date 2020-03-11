package com.learncorooutines.example

import mu.KLogging
import java.util.concurrent.CompletableFuture


class TokenRetrieverAsync {

    companion object : KLogging()

    private fun retrieveToken(): String {
        logger.info("retrieveToken Thread is ${Thread.currentThread().name}")
        return "token"
    }

    private fun externalCall(token: String): String {
        logger.info("externalCall Thread is ${Thread.currentThread().name}")
        logger.info("Invoked the external servie")
        return "Success"
    }

    fun invokeServiceAsync() {
        CompletableFuture.supplyAsync { retrieveToken() }
            .thenAccept { token ->
                CompletableFuture.supplyAsync  {
                    val result = externalCall(token)
                }
            }
    }
}

fun main(args: Array<String>) {
    //TokenRetriever().invokeService()
    TokenRetrieverAsync.logger.info("Current Thread name ${Thread.currentThread().name}")
    TokenRetrieverAsync().invokeServiceAsync()
    TokenRetrieverAsync.logger.info("main method execution ends in ${Thread.currentThread().name}")

}