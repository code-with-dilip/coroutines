package com.learncoroutines.example

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mu.KLogging

class TokenRetrieverAsyncAwait {

    companion object : KLogging()

    fun firstCall(): String{

        return "firstCall"
    }

    fun secondCall(): String{

        return "secondCall"
    }

   /*suspend fun invokeAsync(): String {

      val deferredResutlt =  GlobalScope.async {
           firstCall()
       }
       return deferredResutlt.await()
    }*/

    suspend fun invokeAsync(): Deferred<String> {

        val deferredResutlt =  GlobalScope.async {
            firstCall()
        }
        return deferredResutlt
    }

}

fun main(args: Array<String>) {
    runBlocking{
        TokenRetrieverCoRoutine.logger.info((" The result is ${TokenRetrieverAsyncAwait().invokeAsync().await()}"))
    }
}
