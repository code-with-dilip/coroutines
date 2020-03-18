package com.learncoroutines.api

import kotlinx.coroutines.delay

open class API {

    open suspend fun fetch(): String {
        println("Inside Fetch")
        return "DATA"
    }
}