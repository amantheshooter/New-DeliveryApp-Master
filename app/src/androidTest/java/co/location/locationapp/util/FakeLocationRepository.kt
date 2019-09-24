package co.location.locationapp.util

import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.data.source.LocationRepository
import co.location.locationapp.data.source.local.LocationDataDao
import co.location.locationapp.data.source.remote.ApiInterface
import io.reactivex.Observable
import resources.*

class FakeLocationRepository constructor(apiInterface: ApiInterface, locationDataDao: LocationDataDao) : LocationRepository(apiInterface, locationDataDao) {

     override fun getLocationData(isPullToRefresh: Boolean, limit: Int, offset: Int): Observable<List<DeliveryLocation>> =
            Observable.just(TestUtil.getLocationDataFromFile("locationdata.json"))


    override fun getLocationDataFromApi(isPullToRefresh: Boolean, limit: Int, offset: Int): Observable<List<DeliveryLocation>> =
            Observable.just(TestUtil.getLocationDataFromFile("locationdata.json"))


    override fun getLocationDataFromDb(limit: Int, offset: Int): Observable<List<DeliveryLocation>> =
            Observable.just(TestUtil.getLocationDataFromFile("locationdata.json"))

}