package co.location.locationapp.repositorytest

import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.data.source.LocationRepository
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import resources.TestUtil

class LocationRepositoryTest {
    @Mock
    private lateinit var locationRepository: LocationRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun notNullCheck() {
        Assert.assertNotNull(locationRepository)
    }

    @Test
    fun testFetchLocationData() {
        Mockito.`when`(locationRepository.getLocationData(false, 0, 10)).thenReturn(Observable.just(TestUtil.getListOfLocation()))
        val testObserver = TestObserver<List<DeliveryLocation>>()
        locationRepository.getLocationData(false, 0, 10)
                .subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValue { it[0].id == TestUtil.getListOfLocation()[0].id }
        testObserver.assertValue { it[0].name == TestUtil.getListOfLocation()[0].name }
        testObserver.assertValue { it[0].imageUrl == TestUtil.getListOfLocation()[0].imageUrl }
    }

    @Test
    fun testFetchLocationDataFromDbOnly() {
        Mockito.`when`(locationRepository.getLocationDataFromDb(0, 10)).thenReturn(Observable.just(TestUtil.getListOfLocation()))
        val testObserver = TestObserver<List<DeliveryLocation>>()
        locationRepository.getLocationDataFromDb(0, 10)
                .subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValue { it[0].id == TestUtil.getListOfLocation()[0].id }
        testObserver.assertValue { it[0].name == TestUtil.getListOfLocation()[0].name }
        testObserver.assertValue { it[0].imageUrl == TestUtil.getListOfLocation()[0].imageUrl }
    }

    @Test
    fun testFetchLocationDataFromApiOnly() {
        Mockito.`when`(locationRepository.getLocationDataFromApi(false, 0, 10)).thenReturn(Observable.just(TestUtil.getListOfLocation()))
        val testObserver = TestObserver<List<DeliveryLocation>>()
        locationRepository.getLocationDataFromApi(false, 0, 10)
                .subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValue { it[0].id == TestUtil.getListOfLocation()[0].id }
        testObserver.assertValue { it[0].name == TestUtil.getListOfLocation()[0].name }
        testObserver.assertValue { it[0].imageUrl == TestUtil.getListOfLocation()[0].imageUrl }
    }

    @Test
    fun testError() {
        Mockito.`when`(locationRepository.getLocationData(false, 0, 10)).thenReturn(Observable.error(RuntimeException()))
        val testObserver = TestObserver<List<DeliveryLocation>>()
        locationRepository.getLocationData(false, 0, 10)
                .subscribe(testObserver)
        testObserver.assertError { it is RuntimeException }
    }
}