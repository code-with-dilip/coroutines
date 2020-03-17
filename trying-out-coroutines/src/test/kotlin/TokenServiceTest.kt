import com.learncoroutines.service.TokenService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TokenServiceTest {

    val tokenService= TokenService()

    @Test
    fun retrieveToken() {
        runBlocking {
            val token = tokenService.retrieveToken()
            assertEquals("Token123",token)
        }
    }
}