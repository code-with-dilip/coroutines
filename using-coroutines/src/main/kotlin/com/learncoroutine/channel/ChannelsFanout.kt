import com.learncoroutine.context.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

@ExperimentalCoroutinesApi
fun produceInfiniteNumbers() : ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {
    var x=1;
    while(true){
        send(x++)
        delay(100)
    }
}

fun consume(id: Int, numbers : ReceiveChannel<Int>) : ReceiveChannel<Int> = CoroutineScope(Dispatchers.Default).produce {

    numbers.consumeEach {
        log("Processor #$id recieved $it")
    }
    println("done")
}

fun main() = runBlocking{

    val producer = produceInfiniteNumbers()
    repeat(5){consume(it, producer)}
    println("launched")
    delay(950)
    producer.cancel()
}