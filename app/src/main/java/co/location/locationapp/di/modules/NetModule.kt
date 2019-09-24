package co.location.locationapp.di.modules

import co.location.locationapp.BuildConfig
import co.location.locationapp.data.source.LocationRepository
import co.location.locationapp.data.source.local.LocationDataDao
import co.location.locationapp.data.source.remote.ApiInterface
import co.location.locationapp.utils.OpenForTesting
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetModule() {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun providesMoshiConverterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun providesRxJava2AdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun providesRetrofit(factory: Converter.Factory, okHttpClient: OkHttpClient, rxJava2AdapterFactory: CallAdapter.Factory)
            : Retrofit {
        return Builder().client(okHttpClient).baseUrl(BuildConfig.URL)
                .addConverterFactory(factory)
                .addCallAdapterFactory(rxJava2AdapterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(
            ApiInterface::class.java)

    @Provides
    @Singleton
    fun providesLocationRepository(locationDataDao: LocationDataDao, apiInterface: ApiInterface): LocationRepository = LocationRepository(apiInterface, locationDataDao)

}