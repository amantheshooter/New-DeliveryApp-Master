package co.location.locationapp.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import co.location.locationapp.BuildConfig
import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.data.source.LocationRepository
import co.location.locationapp.ui.home.enums.State
import co.location.locationapp.ui.home.pagingsource.LocationPageKeySource
import co.location.locationapp.ui.home.pagingsource.LocationPageKeySourceFactory
import javax.inject.Inject

private const val PREFETCH_DISTANCE = 3

class HomeViewModel @Inject constructor(
        locationRepository: LocationRepository) : ViewModel() {

    lateinit var deliveryLocationList: LiveData<PagedList<DeliveryLocation>>
    private val locationPageKeySourceFactory: LocationPageKeySourceFactory

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(BuildConfig.PAGE_SIZE)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setEnablePlaceholders(false)
                .build()

        locationPageKeySourceFactory = LocationPageKeySourceFactory(locationRepository)
        deliveryLocationList = LivePagedListBuilder(locationPageKeySourceFactory, config).build()
    }


    fun getStateObserver(): LiveData<State> = Transformations.switchMap<LocationPageKeySource,
            State>(locationPageKeySourceFactory.locationDataSourceLiveData, LocationPageKeySource::state)


    fun getErrorObserver(): LiveData<Throwable> = Transformations.switchMap<LocationPageKeySource,
            Throwable>(locationPageKeySourceFactory.locationDataSourceLiveData, LocationPageKeySource::error)

    fun retry() {
        locationPageKeySourceFactory.locationDataSourceLiveData.value?.retry()
    }

    fun loadRefreshData() {
        locationPageKeySourceFactory.locationDataSourceLiveData.value?.invalidate()
    }

    fun listIsEmpty() = deliveryLocationList.value?.isEmpty() ?: true


    override fun onCleared() {
        super.onCleared()
        locationPageKeySourceFactory.locationDataSourceLiveData.value?.compositeDisposable?.dispose()
        LocationPageKeySource.pageNumber = 1
    }
}