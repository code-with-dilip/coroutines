# Co-Routines

## Why Asynchronous Programming is needed ?

-   In today's software development, its pretty common to make calls to multiple services and return a consolidated response.
-   The execution in general is top-down approach.

**Example**  

-   In this example the code execution goes step by step and at each step the code execution is blocked and the thread executing the code waits for each step to complete.

```aidl
fun invokeService() {
        val token = retrieveToken() //  1. calls and waits 
        val result = this.externalCall(token) // 2. calls and waits
        println("Result is $result") // final response
    }
```

## How to avoid waiting time for each thread?

-   You have three options when it comes to avoiding waiting times
    -   Thread Pool and assign a thread for each request
    -   CallBacks
    -   Futures/Promises/Rx

### Thread Pool - Assign a Thread for each request

-   Make the code async and assign a thread to each call using a Thread Pool.
-   How many threads can we use in a Thread Pool ?
     -  You cannot have many threads because threads typically occupy a memory of close to 1-2 MB.
     
### CallBacks

-   Make the code async by passing a callback to each call.
-   With CallBack things get messy and the code gets more verbose and code can get out of your hand pretty fast.
-   The very popular term is the **CallBack Hell** 

### Future/Promises/Rx

-   The response will be wrapped in a Future
-   This is better and its composable.
-   Example is given below and this code still works very well. 
```aidl
    fun invokeServiceAsync() {
        CompletableFuture.supplyAsync { retrieveToken() }
            .thenAccept { token ->
                CompletableFuture.supplyAsync  {
                    val result = externalCall(token)
                }
            }
    }
```

**DrawBack** 
-   In order to make your code async, you need to learn a totally different library in Kotlin.
-   But the issue here is you need to learn about all the combinators that are mentioned above **thenaccept**. But in reality there are more


## Kotlin Co-Routines

-   Kotlin coroutines are going to let you write the code in a natural way.
-   Code looks like you writing the blocking code.

### suspend functions

-   This is an indicator for Kotlin that this function will be executed asynchronously.

```aidl
suspend fun invokeService() {
        val token = retrieveToken()
        val result = this.externalCall(token)
        logger.info("Result is $result")
    }
```   

### How to invoke a Coroutine? - Using CoroutineBuilders

-   The normall way of invoking the coroutine using the **coroutine builder**
    -   **launch{}** is basically a coroutine builder which behaves like a fire and forget call
        -   This creates a coroutine and forget about it
        -   The execution happens behind the scenes in a thread pool 
    -   **async{}**
        -   returns a value from a coroutine.
        -   Use **async** when there is a value thats going to be returned from the coroutine.

```aidl
 GlobalScope.launch {
        TokenRetrieverCoRoutine().invokeService()
    }
```
- Invoking coroutine in a loop. No special code is needed if corouteine is invoked in a loop.

```aidl
(0..9).forEach {
        GlobalScope.launch {
            TokenRetrieverCoRoutine().invokeService()
        }
    }
```

## TODO

- await() when would you use it?
     