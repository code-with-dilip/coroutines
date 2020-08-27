# Coroutines Under the Covers

Coroutines uses the Continuation Passing Style Code(CPS)

-   Any Suspending Function resolve to **Continuation**. 
-   Continuation is a new term in Kotlin
-   How does the Kotlin Compiler resolves the code to CPS?    

## What is a Continuation?

-   A continuation is a generic callback interface with the following:
    -   context
        -   CoroutineContext
    -   resumeWith
        -   Either provide the result or the function

```kotlin
public interface Continuation<in T> {
    public val context: CoroutineContext
    public fun resume(value: T)
    public fun resumeWithException(exception: Throwable)
}
```
-   Each suspension point resolves to a continuation

### Suspending Function to Continuation - How it works ?

- When Kotlin Compiler compiles the supsending function it converts to a continuation
 
```aidl
suspend fun retrieveItem(coroutineScope: CoroutineScope): String {
        val token = coroutineScope.async(Dispatchers.IO) { getToken() }
        val data = coroutineScope.async(Dispatchers.IO) {  invokeService(token.await()) }
        return data.await()
 }
```

- The kotlin compiled code will look like below with the additional **Continuation** parameter to the function.

```kotlin
```aidl
    suspend fun retrieveItem(coroutineScope: CoroutineScope, continuation: Continuation<String>): String {
        val sm = object: CoroutineImple{...} // sm gets initialized
            //label 0
        switch(label){
            case 0:
                sm.label = 1 //next invocation label value
                val token = coroutineScope.async(Dispatchers.IO) { getToken(sm) } //sm is aded as an argument, when finished call us back
            case 1:
            val data = coroutineScope.async(Dispatchers.IO) {  invokeService(token.await(),sm) //sm is aded as an argument
            return data.await()
           }
        }
     }
```  

-   THe first thing that happens is a state machine gets initialized

```kotlin
        val sm = object: CoroutineImple{...} // sm gets initialized
```
-   The sm gets passed to all the suspending function as an argument. Check the example above
-   When getToken() finishes, it invokes the resume() in the continuation which in-turn invokes the retrieveItem(this) with the next label
-   Process continues until all the labels are invoked

## How Await() works ?

- await() concept is implemented using the **suspendCoroutine**
    -   When **await()** is invoked then it returns immediately(Check 16:43)
-   suspendCoroutine{}
    -   This is a suspending function   
    -   Inside the lamda it accept the regular code that does not have a suspend modifier
    -   This is just a opposite of coroutinebuilder
        -   launch{} -> This call returns immediately
        -   But the argument to the lamda body is a regular function
        ```kotlin
                suspend fun hello() = suspendCoroutine<String> {
                        "Hello"
                    }            
        ```        

## What is a CoroutineContex?

-   CoroutineContext is mainly used to control the thread execution of a given coroutine
-   This is just a map of elements and each element has a key
-   ContextInterceptor is used to handle the thread contention
    -   This is an interceptor which takes in a **continuation** and returns a **continuation**

-   Accessing elements in the CoroutineContext 

```kotlin
    val job = coroutineContext[Job]
    val interceptor = coroutineContext[CoroutineInteceptior]
```           

## Communicating Sequential Processes (CSP)
-   It's a style of programming
    

    

## Step by Step from suspending function to Continuation

### Types of Continuation
-   Initial Continuation - The time when the suspending function got invoked
-   Each suspension point resolves to a continuation
-   What does a continuation have?
    -   The continuation has the following: This is similar to a callback.
        -   Context
        -   resume
        -   resumeWithException
        
        
##  How does the Kotlin Compiler resolves the code to CPS?

-   It first analyses the code and label each suspension points
-   It labels the suspension points first as you can see below.    

```aidl
    suspend fun retrieveItem(coroutineScope: CoroutineScope): String {
            //label 0
            val token = coroutineScope.async(Dispatchers.IO) { getToken() }
            //label 1
            val data = coroutineScope.async(Dispatchers.IO) {  invokeService(token.await()) }
            return data.await()
     }
```
-   The next step the compiler does is to convert the code to a something like a switch case

```aidl
    suspend fun retrieveItem(coroutineScope: CoroutineScope): String {
            //label 0
        switch(label){
            case 0:
                val token = coroutineScope.async(Dispatchers.IO) { getToken() }
            case 1:
            val data = coroutineScope.async(Dispatchers.IO) {  invokeService(token.await())
            return data.await()
           }
        }
     }
```  
### State Machine

-   The next step the compiler does is that it creates a state for the suspending function
    -   The state object maintains the current executing label and where to go next.
```aidl
    suspend fun retrieveItem(coroutineScope: CoroutineScope): String {
        val sm = object: CoroutineImple{...} // sm gets initialized
            //label 0
        switch(label){
            case 0:
                val token = coroutineScope.async(Dispatchers.IO) { getToken(sm) } //sm is aded as an argument
            case 1:
            val data = coroutineScope.async(Dispatchers.IO) {  invokeService(token.await(),sm) //sm is aded as an argument
            return data.await()
           }
        }
     }
```  

-   The state machine is passed as an argument to the first suspending function and invoke the state machine when done with your execution
    -   This is nothing, but the continuation which has the **resumeWith** function
-   The advantage of State Machine over callbacks is that the same object gets reused for every suspension point.
     -  Callbacks creates a new closure everytime you have to pass on to the next step in the code execution.

```aidl
    suspend fun retrieveItem(coroutineScope: CoroutineScope): String {
        val sm = object: CoroutineImple{...} // sm gets initialized
            //label 0
        switch(label){
            case 0:
                sm.label = 1 //next invocation label value
                val token = coroutineScope.async(Dispatchers.IO) { getToken(sm) } //sm is aded as an argument, when finished call us back
            case 1:
            val data = coroutineScope.async(Dispatchers.IO) {  invokeService(token.await(),sm) //sm is aded as an argument
            return data.await()
           }
        }
     }
```  

-   When the below code completes then the **resumeWith** gets invoked in the **ContinuationImpl**
    -   Which will invoke the **retrieveItem** function again with the new label 

```kotlin
 case 0:
    sm.label = 1 //next invocation label value
    val token = coroutineScope.async(Dispatchers.IO) { getToken(sm) } //sm is aded as an argument, when finished call us back
```

