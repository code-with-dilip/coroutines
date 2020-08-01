package com.learncoroutines.service

import com.learncoroutines.util.log
import kotlinx.coroutines.*

class CoroutineService {

    suspend fun launchTest(scope: CoroutineScope) {
        scope.launch { // this call does not block the calling thread
            delay(1000)
            log("First Launch")
        }

        scope.launch { // this call does not block the calling thread
            delay(500)
            log("Second Launch")
        }
        log("launchTest")
    }

    suspend fun asyncTest(scope: CoroutineScope): String {
        val async1 = scope.async {  // this call does not block the calling thread
            delay(1000)
            log("First async")
            return@async "Hello"
        }

        val async2 = scope.async {// this call does not block the calling thread
            delay(500)
            log("Second async") // This executes first because the delay is just 500 ms
            return@async " World"
        }

        log("asyncTest")
        return async1.await()+async2.await()
    }

    suspend fun withContextTest(){
        withContext(Dispatchers.IO){
            delay(1000)
            log("First withContext")
        }

        withContext(Dispatchers.IO){
            delay(500)
            log("second withContext")
        }
        log("withContextTest")

    }
}