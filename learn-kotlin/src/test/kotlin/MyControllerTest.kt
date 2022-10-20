import com.chenluo.test.kotlin.controller.MyController
import com.chenluo.test.kotlin.service.MyService
import com.chenluo.test.kotlin.service.MyServiceImpl
import com.chenluo.test.kotlin.sharedservice.MySharedService
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension

class MyControllerTest {

//    @Mock
    private lateinit var mySharedService: MySharedService

//    @Spy
    private lateinit var myService: MyService

//    @InjectMocks
    private lateinit var myController: MyController

    @Before
    fun init() {
//        MockitoAnnotations.openMocks(this);
//        mySharedService = Mockito.mock(MySharedService::class.java)
//        myService = Mockito.spy(MyServiceImpl(mySharedService))
//        myController = MyController(myService)
        mySharedService = mockk<MySharedService>()
        myService = MyServiceImpl(mySharedService)
        myController = MyController(myService)
    }

//    @Rule
//    var rule: MockitoRule = MockitoJUnit.rule()

    @Test
    fun `test serve 1`() {
//        Mockito.`when`(mySharedService.serve()).thenReturn(true)
        every { mySharedService.serve() } returns true
        assert(myController.serveByMyService())
    }
}