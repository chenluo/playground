import com.chenluo.test.kotlin.controller.MyController
import com.chenluo.test.kotlin.service.MyService
import com.chenluo.test.kotlin.sharedservice.MySharedService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MyControllerTest {

    @MockK
    private lateinit var mySharedService: MySharedService

    @MockK
    private lateinit var myService: MyService

    @InjectMockKs
    private lateinit var myController: MyController

    @Test
    fun `test serve 1`() {
        every { myController.serveByMyService() } returns true
        assert(myController.serveByMyService())
    }
}