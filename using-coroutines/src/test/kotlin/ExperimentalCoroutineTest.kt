import com.learncoroutine.experiment.task1
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExperimentalCoroutineTest {

    @Test
    fun task1Test() {
        runBlocking {
            val response = task1()
            assertEquals("Hello", response)
        }

    }
}