package com.learncoroutines.venkat

import kotlinx.coroutines.*
import mu.KLogging
import mu.KotlinLogging
import java.lang.Thread.sleep

class Logger{
    companion object : KLogging()
}

fun subtask1() : String{

    Logger.logger.info("Inside subtask1")
    sleep()
    return "Hello"

}

fun subtask2() : String{

    Logger.logger.info("Inside subtask2")
    sleep()
    return "Hello"

}
suspend fun taskwithDelay(){

    Logger.logger.info("Inside taskwithDelay")
    withContext(Dispatchers.Default){
        subtask1()
    }
    subtask2()
    withContext(Dispatchers.Default) {
        sleep()
    }
    Logger.logger.info("After Second Sleep")

}

fun sleep() {
    sleep(1000)
}


fun main() {

    runBlocking {
        Logger.logger.info("My context is: $coroutineContext")
        launch(Dispatchers.Default) { taskwithDelay() }
        Logger.logger.info("After the Launch")
    }




}