# Cancellation/Exceptions in Coroutines

-   Anytime cancellation/exception in coroutines 
    -   We need to handle exceptions
    -   We need to clean up resources that was created by coroutines
-   Cancellation is a concept that will happen only in the mobile world

## CoroutineScope
-   This is the entry point to coroutines
-   You can only create coroutines or invoking suspending functions only from inside coroutine scopes
-   Keeps track of coroutines
-   Ability to cancel ongoing work
-   Notified when a failure happens    

## Job

-   A job gives you the handle of the coroutine
-   A job provides the lifecycle to the coroutine
```aidl
 val scope = CoroutineScope(Job())
    val job = scope.launch {
        println("Inside Launch")
    }
```

### Job LifeCycle
-   A job in general goes through different **States**
    -   New, Active
    -   Completing, Completed
    -   Cancelling, Cancelled
-   In order to access these states, we have properties that are part of the **Job** instance
    -   isActive
        -   true -> Once the coroutines are started
    -   isCompleted
        -   true -> Once all the child coroutines are completed
        -   false -> If some child coroutines are still executing
    -   isCancelled
        -   true -> coroutine is cancelled because of an exception or unexpected event happened while the coroutine was in the **active** or **completing** state.
-   By Default, coroutines are launched eagerly, which means they are in active state

#### Starting Coroutine Lazily

-   To start lazily, You need to pass the start attribute as part of the coroutine builder.
-   Explicitly call start method in the **start()** method in the Job instance 

```aidl
val job = scope.launch(start = CoroutineStart.LAZY) {
        println("Inside Launch")
    }

job.start()
```
## CoroutineContext
-   Defines the behavior of the coroutine
-   It has the following elements  

| Elements  | Description | Defaults |
| ------------- | ------------- | ------------- |
| CoroutineDispatcher  | Threading Model |Dispatchers.DEFAULT  |
| Job  | No Parent Job  |Content Cell  |
| CoroutineExceptionHandler  | Handling exceptions in coroutine |none  |
| name  | Name of the coroutine  | coroutine  |

-   A new coroutine inherits its parent context
-   The parent context of the new coroutine is explained using the below formula
```aidl
Parent context = Defaults + inherited CoroutineContext + arguments
```
-   The arguments passed in the coroutine builder override the inherited context
-   The **coroutineContext** of the coroutine
```
coroutineContext = parentContext+Job()
``` 

## ParentCoroutine Scope

-   ParentScope has the capability to track all the coroutines

### Cancelling a Scope

-   Cancellation from the parent scope triggers the cancellation of all child coroutines
```

    val parentScope = CoroutineScope(Job())
    val firstJob = parentScope.launch {
        println("first")
    }
    val secondJob = parentScope.launch {
        println("second")
    }

    parentScope.cancel()
```

### CancellationException ( Happens only in mobile world)
-   This is a special exception to differentiate between a cancellation and other exception thrown

