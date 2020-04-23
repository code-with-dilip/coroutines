package com.learncoroutines.service

import com.learncoroutines.domain.Airport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AirportClient(val airPort : Airport,
                    val airportServic: AirportService
) {

    suspend fun getAirportStatusAsync(airportCode: String): Airport =
        withContext(Dispatchers.IO){
            return@withContext airportServic.getAirportData(airportCode)
        }

}