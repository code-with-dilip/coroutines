package com.learncoroutines.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CoroutineServiceTest {
    val coroutineService = CoroutineService()

    @ExperimentalCoroutinesApi
    @Test
    fun launch() {

        runBlocking {
            coroutineService.launchTest(this)
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun async() {

        runBlocking {
            val response  = coroutineService.asyncTest(this)
            Assertions.assertEquals("Hello World", response)
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun withContextTest() {

        runBlocking {
            coroutineService.withContextTest()
        }

    }
}