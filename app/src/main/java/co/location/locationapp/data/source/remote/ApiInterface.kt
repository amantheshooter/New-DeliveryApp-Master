package co.location.locationapp.data.source.remote

import co.location.locationapp.data.model.DeliveryLocation
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("deliveries/")
    fun getLocationData(@Query("offset") offSet: Int, @Query("limit") limit: Int): Single<List<DeliveryLocation>>
}