package com.learncoroutines.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.learncoroutines.annotations.Open
import com.learncoroutines.domain.Airport

//@Open
open class AirportService {
    val objectMapper = ObjectMapper()


    fun getAirportData(code: String): Airport {
        val airport = Airport.objectMapper.readValue(Airport.fetchData(code), Airport::class.java)
        return airport
    }

    fun fetchData(code: String): String {
        throw RuntimeException("Not Implemented Yet for $code")
    }


}
