package com.learncoroutines.exceptions

import kotlinx.coroutines.*
import java.lang.RuntimeException

class ExceptionCoroutineLaunch {

    fun hello(){
        println("hello")
    }

    fun world() {
       // println("world")
        throw RuntimeException("Exception Occurred")
    }
}

fun main() {

    runBlocking {
        val exceptionCoroutine = ExceptionCoroutineLaunch()
        val scope = CoroutineScope(Job())
        scope.launch { exceptionCoroutine.hello() }
        try{
          val job =  scope.launch { exceptionCoroutine.world() }
            job.join()
        }catch (e: Exception){
            println("Exception handled")
        }

    }
}