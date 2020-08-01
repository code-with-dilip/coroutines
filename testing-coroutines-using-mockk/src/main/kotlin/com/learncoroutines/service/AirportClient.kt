package com.learncoroutines.service

import com.learncoroutines.domain.Airport
import com.learncoroutines.util.log
import kotlinx.coroutines.*

class AirportClient(
    val airPort: Airport,
    val airportServic: AirportService
) {

    suspend fun getAirportStatusAsync(airportCode: String): Airport? {

        try {
            return withContext(Dispatchers.IO) {
                log("Inside getAirportSync IO")
                return@withContext airportServic.getAirportData(airportCode)
            }
        } catch (ex: Exception) {
            println("Exception is : $ex")
        }
        return  null
    }



    suspend fun launchException(scope: CoroutineScope) {

        withContext(Dispatchers.Default){
            delay(1000)
            log("First Launch")
        }
        withContext(Dispatchers.Default){
            delay(500)
            log("Second Launch")
        }
        delay(200)
        scope.launch {
            try {
                airportServic.fetchData()
            } catch (e: Exception) {
                println("Exception is $e")
                throw e
            }
        }
    }


    suspend fun awaitException(scope: CoroutineScope) {

        delay(200)
        val async = scope.async {
            airportServic.fetchData()
        }
        try {
          //  async.await()
        } catch (ex: Exception) {
            println("Exception in await $ex")
        }
    }

    suspend fun awaitCoroutineScope(): String? = coroutineScope {

        delay(200)
        val async = async {
            airportServic.fetchData()
        }
        try {
            return@coroutineScope async.await()
        } catch (ex: Exception) {
            println("Exception in awaitCoroutineScope $ex")
        }
        return@coroutineScope null
    }


}