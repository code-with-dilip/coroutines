package com.learncoroutines.util

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
