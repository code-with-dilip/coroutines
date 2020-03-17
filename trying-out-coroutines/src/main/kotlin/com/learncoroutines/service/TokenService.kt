package com.learncoroutines.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class TokenService {

    private suspend fun service1(): String {
        delay(1000)
        return "Token";
    }

    private suspend fun service2(): String {
        delay(1000)
        return "123";
    }

    suspend fun retrieveToken() : String{
        val scope = CoroutineScope(Dispatchers.IO)
        val tokenPart1 = scope.async { service1() }
        val tokenPart2 = scope.async { service2() }

        return tokenPart1.await()+tokenPart2.await()
    }
}