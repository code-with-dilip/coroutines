package com.learncoroutine.context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

suspend fun main(){

    val scope = CoroutineScope(Dispatchers.Default)
    val job = scope.launch {
        log("invoked inside localscopoe")
    }
    job.join()
}