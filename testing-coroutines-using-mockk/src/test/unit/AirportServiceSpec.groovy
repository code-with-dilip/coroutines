import com.learncoroutines.domain.Airport
import com.learncoroutines.service.AirportClient
import com.learncoroutines.service.AirportService
import org.junit.Ignore
import spock.lang.Specification

import static kotlinx.coroutines.BuildersKt.runBlocking

@Ignore
class AirportServiceSpec extends  Specification{

    def airPort = new Airport()
    def airPortService = Mock(AirportService)
    def airportService = new AirportClient(airPort,airPortService)


    def "getAirportStatusAsync"() {

        given:
        def airportCode = "IAD"
        def iad = new Airport(airportCode, "Dulles", true)
        airPort.getAirportData(_) >> iad

        expect:
        runBlocking(){
            def airport = airportService.getAirportStatusAsync(airportCode)
            assert airPort!=null
            println("airport $airport")
        }


    }


}
