package co.location.locationapp.util

import android.util.Log
import co.location.locationapp.data.source.remote.ApiInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.okhttp.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

open class MockServerBuilder {

    protected lateinit var service: ApiInterface
    protected lateinit var mockWebServer: MockWebServer

    protected fun createService() {
        mockWebServer = MockWebServer()
        val converterFactory: MoshiConverterFactory = MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()).asLenient()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/").toString())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiInterface::class.java)
    }

}