package com.learncoroutines.service

import com.learncoroutines.domain.Airport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


fun getAirportStatus(airportCodes: List<String>): List<Airport> =
    Airport.sort(
        airportCodes.map { code -> Airport.getAirportData(code) })

suspend fun getAirportStatusAsync(airportCodes: List<String>): List<Airport> =
    withContext(Dispatchers.IO){
        Airport.sort(
            airportCodes.map { code -> Airport.getAirportData(code) })
    }
