import com.learncoroutine.builders.dowork
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CoroutineBuilderTest {


    @Test
    fun test() {

        assertEquals("abc", "abc")
    }

    @Test
    fun firstCoroutineTest() {
        runBlocking {
            dowork()
        }
    }
}