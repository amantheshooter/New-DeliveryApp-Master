package co.location.locationapp.ui.home.pagingsource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.data.source.LocationRepository

class LocationPageKeySourceFactory(private val locationRepository: LocationRepository)
    : DataSource.Factory<Int, DeliveryLocation>() {

    val locationDataSourceLiveData = MutableLiveData<LocationPageKeySource>()

    override fun create(): DataSource<Int, DeliveryLocation> {
        val locationPageKeySource = LocationPageKeySource(locationRepository)
        locationDataSourceLiveData.postValue(locationPageKeySource)
        return locationPageKeySource
    }
}