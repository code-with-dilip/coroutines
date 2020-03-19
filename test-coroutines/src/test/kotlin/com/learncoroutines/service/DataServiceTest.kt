package com.learncoroutines.service

import com.learncoroutines.api.API
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DataServiceTest {

    val api = API()

    val api2 = SuspendingFakeAPI()
    val dataService = DataService(api)
    val dataService2 = DataService(api2)

    @Test
    fun loadData() {
        runBlockingTest {

            val result = dataService.loadData()
            println("result : $result")
            assertEquals("DATA", result)
        }

    }

    @Test
    fun loadData_TimeOutException() {
        runBlockingTest {
            try {
                dataService2.loadData()
            } catch (ex: TimeoutCancellationException) {
                println("Inside Exception")
                assertEquals("Timed out waiting for 5000 ms", ex.message)
            }
        }
    }

    @Test
    fun loadData_OwnScope() {

        runBlockingTest {
            val result = dataService.loadData_OwnScope()
            assertEquals("DATA", result)

        }
    }

    @Test
    fun loadData_Scope() {

        runBlockingTest {
            val result = dataService.loadData_Scope()
            assertEquals("DATA", result)

        }
    }

    @Test
    fun retrieveDataFromService() {
        runBlockingTest {
            val data = dataService.retrieveDataFromService(this)
            assertEquals("ExternalCall", data)
        }
    }

    @Test
    fun loadData_TimeOutException_approach2() {
        runBlockingTest {
            try {
                launch { dataService2.loadData() }
                println("After the call")
                advanceTimeBy(5_000)
                api2.deferred.complete("DATA1")
                /*println(response)
                assertTrue( response)*/
            } catch (ex: TimeoutCancellationException) {
                println("Inside Exception")
                assertEquals("Timed out waiting for 5000 ms", ex.message)
            }
        }
    }
}

class SuspendingFakeAPI : API() {

    val deferred = CompletableDeferred<String>()
    override suspend fun fetch() = deferred.await()

}