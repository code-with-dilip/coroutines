package com.learncoroutines.service

import com.learncoroutines.api.API
import kotlinx.coroutines.*

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

class DataService(val api: API) {
    suspend fun loadData() = withTimeout(5_000) {
        return@withTimeout api.fetch()
    }

    suspend fun loadData_OwnScope(): String {
        val scope = CoroutineScope(Dispatchers.IO)
        val deferred = scope.async { api.fetch() }
        return deferred.await()
    }

    suspend fun loadData_Scope(): String {

        return withContext(Dispatchers.IO) {
            val deferred = async { api.fetch() }
            deferred.await()
        }
    }

    suspend fun retrieveDataFromService(scope: CoroutineScope): String {
        log("inside retrieveDataFromService")
        val data = scope.async { externalService(scope) }
        return data.await()
    }

    private suspend fun externalService(scope: CoroutineScope): String {
        log("inside externalService")
        return withContext(scope.coroutineContext) {
            log("inside with Context")
            "ExternalCall"
        }
    }
}