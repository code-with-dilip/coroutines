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

-   **withTimeout** or **withTimeoutOrNull** are the two methods that can be used to timeout on a coroutine
    -   withtimeout -> This throws an exception in the event of a timeout
    -   withTimeoutOrNull -> This returns null once the time is elapsed.
    
#### Example: 
```aidl
fun main() {
    runBlocking {
        //val job = withTimeout(100) {
        val job = withTimeoutOrNull(100) {
            repeat(1000) {
                yield()
                print(".")
                Thread.sleep(1)
            }
        }
        delay(100)

        if(job==null){
            println("timed out")
        }

    }
}
```

## CoroutineContext

-   All Coroutines run as part of the CoroutineContext.
    -   This determines how the coroutine is going to behave.
    -   The CoroutineContext is created by the launcher.
    -   The context provides a **dispatcher** which determines which Thread is going to run the coroutine
-   The Context has the following Dispatcher has the following options  
    -   UnConfined
        -   This executes on the thread where the coroutine is executed
        -   It switches to a different thread based on the suspending function thats used within the coroutine
    -   coroutineContext
    -   CommonPool
    -   newSingleThreadContext
    -   DefaultDispatcher
     
### UnConfined
-   This executes on the thread where the coroutine is executed
-   It switches to a different thread based on the suspending function thats used within the coroutine

```
fun main() = runBlocking {
    val jobs = arrayListOf<Job>()
    jobs += launch(Dispatchers.Unconfined) {
        println("coroutineContext : I am working in thread [${Thread.currentThread().name}]")
        delay(100)
        //yield()
        println("coroutineContext : After Delay in thread [${Thread.currentThread().name}]")
    }

}
```

### Accessing a Job from coroutinecontext

-   The **coroutinecontext** has the handy method to access the 

```aidl
val job = launch {
        println("isActive ? : ${coroutineContext[Job]!!.isActive}")
        println("coroutineContext : $coroutineContext")
    }
```

### Parent-Child Coroutines

-   Normally this relationship is established only when coroutines are nested.

-   The only to establish the relationship is by passing the **coroutineContext** to the child coroutine.

```aidl
 val outer = launch { //outer coroutine
        launch(coroutineContext) {//inner coroutine
            repeat(1000) {
                println("$it")
                delay(1)
            }
        }

    }
    outer.join() // this will wait until the child coroutine completes its execution
```

#### Canceling the Coroutine in a parent/child relationship

- Cancelling the parentcoroutine automatically cancels the child coroutine too

```aidl
outer.cancelAndJoin()
```

-   Cancelling just the children coroutine

```aidl
    outer.cancelChildren()
```

## Returning Data From Coroutines

-   Use **async** Coroutine Builder in case of getting the data from the coroutine builder
-   **async** coroutine builder returns a **Deferred** object.
    -   This coroutinebuilder invokes the function and returns immediately with the **Deffered** object
-   **await()**
    -   This function waits until the Deferred object completes and this is a blocked call.     

```aidl
fun main() {

    val scope = CoroutineScope(Dispatchers.Default)
    val job = scope.launch {
        val r1 = async { doWorkOne() }

        val r2 = async { doWorkTwo() }
        log("result : ${r1.await() + r2.await()}")
    }
    runBlocking {
        job.join()
    }

}
``` 

- **AsyncExample2**

-   In this below example,
    -   we have an **async** call and other is a regular method call inside the **coroutine**
    -   **async** gets executed later after the regular method as per the code is designed 
    -   The code gets executed concurrently

```aidl
suspend fun doWork(msg: String): String {
    log("Working $msg ")
    delay(300)
    log("Working $msg")
    return "Hello"
}


fun main() {

    runBlocking {
        val job = launch {
            val r1 = async { doWork("First Job") }
        }
        doWork("Second Job")
        job.join()
    }

}
```

### Make the whole function async

```aidl
fun main() {
    val result = doWorkAsync("Hello")
    runBlocking {
        result.await()
    }
}

fun doWorkAsync(msg: String)  : Deferred<String> =  CoroutineScope(Dispatchers.IO).async {
    log("Started Working $msg ")
    delay(300)
    log("Completed Work $msg")
    return@async "Hello"

}
```
### Starting async Functions Lazily

-   The actual coroutine gets executed only when we call the await method

```aidl
package com.learncoroutine.async

import com.learncoroutine.context.log
import kotlinx.coroutines.*


suspend fun doWorkLazy(msg: String): String {
    log("Working $msg ")
    delay(200)
    log("Completed Work $msg")
    return "Hello"
}


fun main() = runBlocking {

    val job = launch {
        val result = async(start = CoroutineStart.LAZY) {
            doWorkLazy("Hello")
        }
        result.await() // This executes the coroutine
    }
    job.join()
    println("Completed")
}


```

## Use Channels to Communicate between Coroutines

-   Single Coroutine can communicate to us by using the deferred object.
-   Coroutines can send to or receive from a channel
    -   send() -> This is a blocking call which blocks the thread until the data is received
    -   receive() -> This is a blocking call which blocks the thread until the data is recieved from the send call

### Simple Send/Recieve Example

-   In the below example we are sending 5 element and we are receiving 5 elements
    -   We have hardcoded the values that will be sent and received by the channel
     
```
fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        for ( x in 1..5){
            log("sending $x")
            channel.send(x)
        }
    }
    repeat(5){
        log("receive from channel ${channel.receive()}")
    }
}
```

### Closing Channels

-   The channel class has a close() method using which we can close the channel

```aidl
channel.close()
```

-   How do we dynamically close the channel
    -   We can iterate through the channel and it will retrieve the values that are available in the channel and releases the call once there are no data in the channel.

```aidl
fun main() = runBlocking {

    val channel = Channel<Int>()
    launch {
        for ( x in 1..5){
            log("sending $x")
            channel.send(x)
        }
        log("Before closing the channel")
        channel.close()
    }
    for (y in channel){
        log("receive from channel $y")
    }
}
```

### Producer/Consumer channel CoroutineBuilder  Functions

-   There is a handy **produce** corouting builder which takes care of producing the elements
    -   This coroutine builder takes care of:
        -   creating the channel
        -   closing the channel
    -   With this we don't have to manually manage the channel
    
```aidl
@ExperimentalCoroutinesApi
fun main() = runBlocking{
    val channel = produceNumbers()
    channel.consumeEach {
        println("Receiving : $it")
    }
}

@ExperimentalCoroutinesApi
fun produceNumbers() : ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {
    for(x in 1..5){
        println("Sending : $x")
        send(x)
    }
    println("done")
}
``` 

### Pipelining Channels

-   We can also join channels in order to pass on the data from one channel
    -   The below code is an example where the **produceInfiniteNumbers()** channel output is sent to the **squareNumbers()** channel
    
```aidl
@ExperimentalCoroutinesApi
fun produceInfiniteNumbers() : ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {
    var x=1;
    while(true){
        send(x++)
    }
}

fun squareNumbers(numbers : ReceiveChannel<Int>) : ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {
    for(x in numbers){
        send(x*x)
    }
    println("done")
}

fun main() = runBlocking{

    val producer = produceInfiniteNumbers()
    val square = squareNumbers(producer)
    for (i in 1..5) println("square numbers are :  ${square.receive()}")

    square.cancel()
    producer.cancel()
}
```

## Use Channels to Fan-Out and Fan-In

### FanOut

-   This is the concept of instantiating more consumers for a given channel

```
    repeat(5){consume(it, producer)} // fanout 5 consumers for a given channel

```

### FanIn

-   This concept is just the opposite of FanOut. In this scenario we have multiple producers and one consumer

```aidl
suspend fun produce(channel : Channel<String>, msg : String, interval: Long){
    while (true){
        delay(interval)
        channel.send(msg)
    }
}

fun main() = runBlocking<Unit> {
    val channel = Channel<String>()
    launch(coroutineContext) {
        produce(channel, "foo", 200L)
    }
    launch(coroutineContext) {
        produce(channel, "bar", 200L)
    }
    repeat(6){
        log(channel.receive())
    }
    coroutineContext.cancelChildren()
}
```

### Buffered Channel

-   This is a concept where you can buffer the elements in the channel until the receiver is up and running and reads the data from the channel

```aidl
fun main() = runBlocking {
    val channel = Channel<Int>(3) // channel buffer of size 3
    val sender = launch(coroutineContext) {
        repeat(10) {
            log("sending $it")
            channel.send(it)
        }
        channel.close()
    }
    delay(1000)
    for (y in channel){
        log("receive from channel $y")
    }
    log("cancel complete")
}
```

## Creating your own Local Scope

```aidl
    suspend fun main(){
    
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
            log("invoked inside localscopoe")
        }
        job.join()
    }
```
