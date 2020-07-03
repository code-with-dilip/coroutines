package com.learncoroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

fun main(){

    val parentScope = CoroutineScope(Job())
    val firstJob = parentScope.launch {
        println("first")
    }
    val secondJob = parentScope.launch {
        println("second")
    }

    parentScope.cancel()

}