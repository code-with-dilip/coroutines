package com.learncoroutines.service

import com.learncoroutines.domain.Airport
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AirportServiceTest {

    val airport = mockk<Airport>()
    val airportService = mockk<AirportService>()
    val airportClient = AirportClient(airport,airportService)
    val iad = Airport("IAD", "Dulles", true)

    @Test
    fun getAirportStatusAsync() {

        coEvery { airportService.getAirportData("IAD") } returns iad

        runBlocking {
            val airport = airportClient.getAirportStatusAsync("IAD")
            assertNotNull(airport)
            println("airport $airport")
        }


    }
}