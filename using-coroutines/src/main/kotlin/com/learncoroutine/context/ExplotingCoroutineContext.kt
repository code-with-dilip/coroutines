package com.learncoroutine.context

import kotlinx.coroutines.*


private fun CoroutineScope.createJobs(jobs: ArrayList<Job>) {
    jobs += launch {
        println("default in thread ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.Default) {
        println("Dispatchers.Default in thread ${Thread.currentThread().name}")
    }

    jobs += launch(Dispatchers.Unconfined) {
        println("Dispatchers.Unconfined in thread ${Thread.currentThread().name}")
    }

    jobs += launch(coroutineContext) {
        println("coroutineContext in thread ${Thread.currentThread().name}")
    }

    jobs += launch(newSingleThreadContext("OwnThread")) {
        println("newSingleThreadContext in thread ${Thread.currentThread().name}")
    }

}



fun main() = runBlocking {

        val jobs = arrayListOf<Job>()
        createJobs(jobs)

        val job = launch {
            println("launched thread ${Thread.currentThread().name}")
            val jobs = arrayListOf<Job>()
            createJobs(jobs)
            jobs.forEach{ it.join()}
        }
        job.join()
        jobs.forEach{ it.join()}


}