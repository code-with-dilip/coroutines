package com.learncorooutines.example

class TokenRetriever {

    private fun retrieveToken(): String {
        return "token"
    }

    private fun externalCall(token: String): String {
        println("Invoked the external servie")
        return "Success"
    }

    fun invokeService() {
        val token = retrieveToken()
        val result = this.externalCall(token)
        println("Result is $result")
    }


}

fun main(args: Array<String>) {
    TokenRetriever().invokeService()
}