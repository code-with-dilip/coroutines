# Coroutine

## What is a Co-Routine?

- A Coroutine is something which can execute a piece of code in concurrent bits.
    -   A **Co-Routine** provides a state for a function
    -   A **Co-Routine** allows the code to have multiple entry point.
    -   CoRoutines can be thought of as a lightweight threads
    -   A coroutine executes a piece of code in a thread
    -   Basically they are state machine objects
        -   Data for each state
        -   index of the current state
        -   Ability to wait patiently 
-   By Default, coroutine is launched eagerly.    

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

## Generate the ByteCode for coroutines

-   Build the project (./gradlew build)

-   Navigate to the build directory. Sample given below

```windows
/Users/z001qgd/Dilip/lightning-talks/coroutines-talk/code-base/couroutines-talk/build/classes/kotlin/main/com/learncoroutines
```
-   Run this command as below. Ignore **Kt.class** file with the same name

```
javap -c LocationAPICallUsingCoroutines 
```

## How Coroutines are executed in the JVM ?

-   **CoroutineSchedulers** is the class that takes executing the tasks submitted to the queue   
    
## What is a CoroutineScope?

-   All Coroutines should be run in a scope.
    -   Keeps track of all the coroutines
    -   Scopes can cancel all coroutines that got created under it
    -   Scopes get notified when uncaught exceptions happens
    -   Use scopes to avoid leaks
-   This is the entry point to your coroutine.     
    
### How to create a CoroutineScope ?

#### Approach 1

-   The below creates a scope with the with the defaults 

```aidl
   val scope = CoroutineScope(Job())

```

#### Approach 2

-   The below creates a scope with the with the values defined in the arguments passed to it
    -   The dispatcher for the scope is IO. So everything will be run in the IO thread.

```aidl
    val scope1 = CoroutineScope(Job() + Dispatchers.IO + CoroutineName("hi"))

```    

## Running Coroutines using Coroutine Builder
-   Coroutines can only be run inside a context 
-   Coroutine Builder is fundamentally used to launch a **Coroutine context**.
-   CoroutineBuilders are the bridges/entry-point to your suspendable code

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

## What is a suspending function?

-   A suspending function suspends its execution until the data is ready and continues further.
-   In this below example, the function suspends its execution for 100 ms and once the time exhausts then it going to execute the code following the delay() call.

```aidl
suspend fun soFun(){
    delay(100)
    println("soFun")
}
```

## Wait, Join and Cancel Coroutines

### Join Coroutine

-   This is used to join multiple coroutines
-   The code gets blocked until the all the joined coroutines are finished

### Job Interface

-   launch() returns a job

-   The Job interface can be used to perform the below operation:
    -   The **Job** provides lifecycle. The **Job** provides you the handle of the coroutine. 
    -    Status of the coroutines ( active, finished, canclled or )
    -    Join coroutines using the **join** method in the Job interface
    

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

##### Approach 2 using isAcive or ensureActive()

- Using the isActive Flag or ensureActive() to check the status of the coroutine. This is very useful to make the coroutine to be cooperative if you have used 
functions that are not cooperative.
 
```aidl
  fun main() {
      runBlocking {
          val scope = CoroutineScope(Job())
          val job = scope.launch {
              repeat(5 ) {
                  sleep(100)
                  //if(isActive) // this makes the coroutines to behave as cooperative
                    ensureActive() // this makes the coroutines to behave as cooperative
                          print("$it")
              }
          }
  //        delay(500)
          delay(200)
          job.cancel()
          println("job cancelled")
      }
      sleep(1000)
  }
```


#### Approach3 using yield()

-   **yield()** is mainly used in cpu intensive tasks that may exhaust the thread pool
    - This also checks whether the coroutine was cancelled. If yes this throws the cancellation exception  

```aidl
fun main() {
    runBlocking {
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            repeat(5) {
                delay(100)
                yield()
                print("$it")
            }
        }
//        delay(500)
        delay(300)
        job.cancel()
        println("job cancelled")
    }
    sleep(1000)
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

#### Approach 1 - using try/catch/finally block

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

#### Approach 2 - using try/catch/finally and withContext block

```aidl
fun main() {

    runBlocking {
        val job1 = launch {
            try {
                repeat(10) {
                    delay(200)
                    yield()
                    print("$it")
                }
            } catch (ex: CancellationException) {
                println("Cancellation exception : ${ex}")
            } finally {
                println("Close Resources if any")
                //this is a special suspending function which takes care changing the context first
                // Noncancellable is a check with is always active irrespective of the coroutine was cancelled.
                withContext(NonCancellable){
                    delay(100)
                    println("Close Resources if any after delay")
                }
            }
        }
        delay(500)
        job1.cancel()
        println("done")
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

## Handling RunTime Exceptions in Coroutines


## CoroutineContext

-   All Coroutines run as part of the CoroutineContext.
    -   This determines how the coroutine is going to behave.
    -   The CoroutineContext is created by the launcher.
    -   The context provides a **dispatcher** which determines which Thread is going to run the coroutine
-   What are the elements does the CoroutineContext have ?
    -   CoroutineDispatcher -> Threading
    -   Job -> LifeCycle
    -   CoroutineExceptionHandler
    -   CoroutineName  
-   A new coroutine inherits the parent context.    

### CoroutineContext Defaults

-   CoroutineDispatcher -> Dispatchers.Default
-   Job -> No Parent Job
-   CoroutineExceptionHandler -> None
-   CoroutineName -> "coroutine"  
      

### CoroutineDispatcher
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

## suspend vs async

| suspend  | async |
| ------------- | ------------- |
| Creates a coroutine | Creates a coroutine   |
| Fire and Forget  | Returns a value  |
| Takes a dispatcher  | Takes a dispatcher  |
| Executed in a scope  | Executed in a scope  |
| Not a suspend function  | Not a suspend function |
| Rethrows exception | Holds on to the exception until await is called |


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

## Waiting on Multiple Coroutines using Select

-   Select allows us to wait on multiple coroutines and  select the first one that becomes available.
   
```aidl
@ExperimentalCoroutinesApi
fun CoroutineScope.producer1() : ReceiveChannel<String> = produce {
    while (true){
        delay(100)
        send("from producer 1")
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.producer2() : ReceiveChannel<String> = produce<String> {
    while (true){
        delay(300)
        send("from producer 2")
    }
}

suspend fun selector(message1: ReceiveChannel<String>, message2: ReceiveChannel<String>){
    select<Unit> {
        message1.onReceive{value -> println(value)}
        message2.onReceive{value -> println(value)}

    }
}

fun main() = runBlocking {
    val m1 = producer1()
    val m2 = producer2()
    repeat(15){
        selector(m1,m2)
    }
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

### Things to read

-   When would you create your own Scope?
-   Why we should not use GlobalScope?
-   
