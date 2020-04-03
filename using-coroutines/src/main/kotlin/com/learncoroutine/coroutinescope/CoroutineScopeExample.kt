package com.learncoroutine.coroutinescope

import com.learncoroutine.context.log
import kotlinx.coroutines.*

suspend fun scopeTask(){
    delay(1000)
    log("Inside Scope Task")

}

fun defaultScope(){

    val scope = CoroutineScope(Job())
    val job = scope.launch {
        scopeTask()
    }
    log(" job : $job")
    log(" scope : $scope")

    log("Done")

    Thread.sleep(2000)
}

fun customScope(){
    val scope1 = CoroutineScope(Job() + Dispatchers.IO + CoroutineName("hi"))
    val job1 = scope1.launch {
        log("coroutineContext : $coroutineContext")
        scopeTask()
    }
    log(" job1 : $job1")
    log(" scope1 : $scope1")

    log("Done with second scope")
    Thread.sleep(2000)
}

fun main() {
    defaultScope()
    customScope()
}
