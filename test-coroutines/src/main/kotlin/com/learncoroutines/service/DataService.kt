package com.learncoroutines.service

import com.learncoroutines.api.API
import kotlinx.coroutines.withTimeout

class DataService(val api : API) {
    suspend fun loadData() = withTimeout(5_000){
        return@withTimeout api.fetch()
    }
}