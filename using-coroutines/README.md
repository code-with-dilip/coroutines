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

## Wait, Join and Cancel Coroutines

### Join Coroutine

-   This is used to join multiple coroutines
-   The code gets blocked until the all the joined coroutines are finished

### Job Interface

-   launch() returns a job
-   The Job interface can be used to perform the below operation:
    -    Join coroutines using the **join** method in the Job interface
    -    Status of the coroutines ( finished, canclled or active)

#### Join Example

```aidl
 runBlocking {

        val job = launch {
            delay(1000)
            println("World")
        }
        println("Hello , ")
        job.join() // This waits until the coroutine completes.
    }
```

### Cancelling Coroutine

-   What if a coroutine runs too long and you would like to cancel it
    -   Cancelling a coroutine should take care of the following
        -   Releasing any resources
        -   Handling Exceptions    
    -   Cancel a coroutine is coperative
        -   All the inbuilt suspending functions are cooperative
            -   delay
            -   yield
    
#### Cancel Example

##### Approach 1
-   job.cancel() and job.join() or job.cancelAndJoin() are the methods that are available as part of the job interface to perform cancel on a coroutine.
 
```aidl
    val job1 = launch {
            repeat(1000) {
                yield()
                //delay(100)
                print(".")

            }
        }        delay(2500)
        /*job1.cancel()
        job1.join()*/
        job.cancelAndJoin()
``` 

-   In the above the code, its the **delay()** or the **yield()** function that's co-operating with each other and perform the cancellation of coroutine.

##### Approach 2

- Using the isActive Flag to check the status of the coroutine. 
```aidl
  val job1 = launch {
            repeat(1000) {
               if(!isActive) throw CancellationException()
                yield()
                //delay(100)
                print(".")
               // Thread.sleep(1)

            }
        }
        delay(10)
        job1.cancelAndJoin()
        println("done")
    }
```

-   Replaced the delay in the launch coroutine with Thread.sleep(). Here in this case the **cancelAndJoin()** wont cancel the coroutine.

```aidl
        val job1 = launch {
            repeat(1000) {
                Thread.sleep(100)
                print(".")
            }
        }
        delay(2500)
        /*job1.cancel()
        job1.join()*/
        job.cancelAndJoin()
```    

### Handling Exceptions in Coroutines

-   Always throw the CancellationException in case of cancelling a **coroutine**.
-   Exceptions in coroutine can be handled by adding a **try/catch** block to the code
-   Have the finally block in there incase of releasing any resources

```aidl
 val job1 = launch {
            try {
                repeat(1000) {
                    //if(!isActive) throw CancellationException()
                    yield()
                    //delay(100)
                    print(".")
                    // Thread.sleep(1)

                }
            } catch (ex: CancellationException) {
                println("Cancellation exception : ${ex}")
            } finally {
                run {
                    println("Close Resources if any")
                }
            }
        }

```

### Adding a Timeout to the coroutine


## Creating your own Scope

```aidl
    val coroutine = CoroutineScope(Dispatchers.Default).launch {

       println("Inside the Coroutine Scope")

    }
```
