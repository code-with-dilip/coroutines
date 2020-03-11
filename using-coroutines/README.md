# Coroutine

## Very First Coroutine

-   The launch function is a coroutine builder.
    -   This launches a coroutine asynchronously in a different thread
-   The **delay(1000)** function is a suspending function and it will delay the coroutine to execute after for 1second
    -   During this time, it releases the thread that was executing the launch coroutine
-   The coroutine comes back to life after a second



```aidl
GlobalScope.launch {
        delay(1000) // This does not block the thread. Releases the thread and have the thread available to run other coroutines
        println("WORLD")
    }
    print("Hello, ")

    sleep(1500)
```

## Running Coroutines using Coroutine Builder
-   Coroutines can only be run inside a context 
-   Coroutine Builder is fundamentally used to launch a **Coroutine context**.

- Some Coroutine Builders are 
    -    launch 
        -    Launches the coroutine and returns immediately
    -   runBlocking - This is mainly used in the scope of testing or in the main function
        -    Launches the coroutine and blocks the thread until the coroutine is executed.
    
### launch Coroutinebuilder

-   The launch coroutinebuilder launches executes immediately
-   This **launch** creates a coroutine and waiting to be executed and it will be scheduled on a thread  
 
```aidl
 GlobalScope.launch {
        delay(1000) // This does not block the thread.
        println("WORLD")
        //coroutine_100000()
    }
    print("Hello, ")
```   
 
-   The delay() code suspends the coroutine to the background and waits for the delay to be finish and then scheduled on a thread to execute the remaining piece of code.
-   The delay() code does not block the thread. It releases the coroutine from the thread and have that available for other tasks to execute.

```aidl
delay(1000)
```

- Making the whole main() function to be a coroutineBuilder 
    -   The whole function below is a nonblocking
    
    ```aidl
           fun main() = runBlocking{
           
               launch {
                   delay(1000) // This does not block the thread. Releases the thread and have the thread available to run other coroutines
                   println("WORLD")
                   //coroutine_100000()
               }
               print("Hello, ")
                   dowork()
           }
           
           suspend fun dowork() {
           
               delay(1500)
           
           }
           ```
