package co.location.locationapp.di

import android.app.Application
import androidx.room.Room
import co.location.locationapp.data.source.local.Database
import co.location.locationapp.data.source.local.LocationDataDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockDatabaseModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(app: Application): Database = Room.inMemoryDatabaseBuilder(app,
            Database::class.java)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideLocationDao(
            database: Database): LocationDataDao = database.locationDataDao()

}