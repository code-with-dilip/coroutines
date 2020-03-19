package com.learncoroutines.api

import com.learncoroutines.service.log
import kotlinx.coroutines.delay

open class API {

    open suspend fun fetch(): String {
        log("Inside Fetch")
        return "DATA"
    }
}