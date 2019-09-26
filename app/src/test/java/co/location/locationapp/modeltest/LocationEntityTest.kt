package co.location.locationapp.modeltest

import co.location.locationapp.data.model.Address
import co.location.locationapp.data.model.DeliveryLocation
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LocationEntityTest {
    private val id = "Testing id"
    private val description = "Testing description"
    private val imageUrl = "www.google.com/image"
    private val address = "Testing address"
    private val latitude = 28.4595
    private val longitude = 77.0266

    @Mock
    internal var locationEntity: DeliveryLocation? = null
    @Mock
    internal var addressEntity: Address? = null


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testId() {
        Mockito.`when`(locationEntity?.id).thenReturn(id)
        Assert.assertEquals("Testing id", locationEntity?.id)
    }

    @Test
    fun testDescription() {
        Mockito.`when`(locationEntity?.name).thenReturn(description)
        Assert.assertEquals("Testing description", locationEntity?.name)
    }

    @Test
    fun testUrl() {
        Mockito.`when`(locationEntity?.imageUrl).thenReturn(imageUrl)
        Assert.assertEquals("www.google.com/image", locationEntity?.imageUrl)
    }

    @Test
    fun testLatitude() {
        Mockito.`when`(addressEntity?.lat).thenReturn(latitude)
        Assert.assertEquals(28.4595, addressEntity?.lat)
    }

    @Test
    fun testLongitude() {
        Mockito.`when`(addressEntity?.lng).thenReturn(longitude)
        Assert.assertEquals(77.0266, addressEntity?.lng)
    }


    @Test
    fun testAddress() {
        Mockito.`when`(addressEntity?.address).thenReturn(address)
        Assert.assertEquals("Testing address", addressEntity?.address)
    }
}