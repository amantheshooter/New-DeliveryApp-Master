package co.location.locationapp.repositorytest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.util.MockServerBuilder
import com.squareup.okhttp.mockwebserver.MockResponse
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import resources.TestUtil
import retrofit2.HttpException

@RunWith(JUnit4::class)
class ApiInterfaceTest : MockServerBuilder() {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        super.createService()
    }

    @Test
    fun testLocationData() {
        mockWebServer.enqueue(MockResponse().setBody(TestUtil.getArrayOfLocation()))
        val testObserver = TestObserver<List<DeliveryLocation>>()
        service.getLocationData(0, 10).subscribe(testObserver)
        val request = mockWebServer.takeRequest()
        Assert.assertThat(request.path, CoreMatchers.`is`("/deliveries/?offset=0&limit=10"))
        testObserver.assertValue { it.isNotEmpty() }
    }

    @Test
    fun testError() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
        val testObserver = TestObserver<List<DeliveryLocation>>()
        service.getLocationData(0, 10).subscribe(testObserver)
        testObserver.assertError { it is HttpException }
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }
}