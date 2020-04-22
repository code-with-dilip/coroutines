package com.learncoroutines.domain

import com.fasterxml.jackson.databind.ObjectMapper

data class Airport(
    val code: String,
    val name: String,
    val delay: Boolean) {

    constructor() : this("", "", false)
    companion object {
        val objectMapper = ObjectMapper()

        fun sort(airports: List<Airport>): List<Airport> {
            return airports.sortedBy { airport -> airport.name }
        }

        fun getAirportData(code: String): Airport {
            val airport = objectMapper.readValue(fetchData(code), Airport::class.java)
            return airport
        }

        fun fetchData(code: String): String {
            throw RuntimeException("Not Implemented Yet for $code")
        }


    }
}