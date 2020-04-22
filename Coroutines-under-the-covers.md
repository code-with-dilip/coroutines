# Coroutines Under the Covers

Corotines uses the Continuation Passing Style Code(CPS)

-   Any Suspending Function resolve to **Continuation**. 
-   Continuation is a new term in Kotlin
-   How does the Kotlin Compiler resolves the code to CPS?    

## What is a Continuation?

-   Continuation is nothing but callbacks.
-   Each suspension point resolves to a continuation

### Suspending Function to Continuation

```aidl
suspend fun loadData(){
    val data = networkRequest()
    show data()
}
```

- Continuation

```aidl
fun loadData(continuation: Continuation){ 
    val data = networkRequest(continuation)
    show(data)
}

```

### Types of Continuation
-   Initial Continuation - The time when the suspending function got invoked
-   Each suspension point resolves to a continuation
-   What does a continuation have?
    -   The continuation has the following: This is similar to a callback.
        -   Context
        -   Resume
        -   ResumeWithException

##  How does the Kotlin Compiler resolves the code to CPS?

- Sample Suspending function
```aidl
    suspend fun invokeService() {
    val token = withContext(Dispatchers.Default) {
                retrieveToken()
            }
      val result = withContext(Dispatchers.Default) {
                externalCall(token)
            }
        logger.info("Result is $result")
    }
```

-   It first analyses the code and label each suspension points
-   It labels the suspension points first as you can see below.    
    ```aidl
    suspend fun invokeService() {
        // label 0
        val token = withContext(Dispatchers.Default) {
            retrieveToken()
        }
        // label 1
        val result = withContext(Dispatchers.Default) {
            externalCall(token)
        }
        logger.info("Result is $result")
    }
    ```          
-   The next step the compiler does is to convert the code to a something like a switch case

```aidl
suspend fun invokeService() {
    switch(label){
    case0:
        val token = withContext(Dispatchers.Default) {
            retrieveToken()
        }        
    case1:
        val result = withContext(Dispatchers.Default) {
            externalCall(token)
        }
    }

}
```    

-   The next step the compile does is that it creates a state for the suspending function
    -   The state object maintains the current executing label and where to go next.
```aidl
suspend fun invokeService() {
val sm = object: CoroutineImple{...}
    switch(label){
    case0:
        sm.label = 1
        val token = withContext(Dispatchers.Default) {
            retrieveToken()
        }        
    case1:
        val result = withContext(Dispatchers.Default) {
            externalCall(token)
        }
    }

}
```
### State Machine
-   The state machine is passed as an argument to the first suspending function
-   The advantage of State Machine over callbacks is that the same object gets reused for every suspension point.
     -  Callbacks creates a new closure everytime you have to pass on to the next step in the code execution.

```aidl
suspend fun invokeService() {
val sm = object: CoroutineImple{
            fun resume(...){
                invokeService(this) // sm knows which case block in label to invoke 
            }
        }
    switch(label){
    case0:
        sm.label = 1 // this saves the state
        val token = withContext(Dispatchers.Default) {
            retrieveToken(sm) // sm is passed as an argument which has the callback to invoke the **invokeService** function
        }        
    case1:
        val result = withContext(Dispatchers.Default) {
            externalCall(token)
        }
    }

}
```

## What is a CoroutineContex?

-   CoroutineContext is mainly used to control the thread execution of a given coroutine

## Kotlin Coroutines in Practice

