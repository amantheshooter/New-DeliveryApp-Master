package co.location.locationapp.data.source

import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.data.source.local.LocationDataDao
import co.location.locationapp.data.source.remote.ApiInterface
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class LocationRepository @Inject
constructor(private val apiInterface: ApiInterface, private val locationDataDao: LocationDataDao) {

    open fun getLocationData(isPullToRefresh: Boolean, limit: Int, offset: Int): Observable<List<DeliveryLocation>> =
            when {
                isPullToRefresh -> getLocationDataFromApi(isPullToRefresh, limit, offset)
                else -> getLocationDataFromDb(limit, offset)
                        .flatMap {
                            if (it.isEmpty()) getLocationDataFromApi(isPullToRefresh, limit, offset) else Observable.fromArray(it)
                        }
            }

    open fun getLocationDataFromApi(isPullToRefresh: Boolean, limit: Int, offset: Int): Observable<List<DeliveryLocation>> =
            apiInterface.getLocationData(offset, limit)
                    .toObservable()
                    .doOnNext {
                        if (!it.isNullOrEmpty()) {
                            if (isPullToRefresh) {
                                deleteDataFromDB()
                            }
                            insertData(it)
                        }
                    }

    open fun getLocationDataFromDb(limit: Int, offset: Int): Observable<List<DeliveryLocation>> =
            locationDataDao.queryLocationData(limit, offset)
                    .toObservable()
                    .subscribeOn(Schedulers.io())

    private fun insertData(deliveryLocationList: List<DeliveryLocation>) {
        Maybe.fromCallable { locationDataDao.insertAllLocationData(deliveryLocationList) }.subscribeOn(Schedulers.io()).subscribe()
    }

    private fun deleteDataFromDB() {
        Maybe.fromCallable { locationDataDao.deleteAllLocationData() }.subscribeOn(Schedulers.io()).subscribe()
    }
}