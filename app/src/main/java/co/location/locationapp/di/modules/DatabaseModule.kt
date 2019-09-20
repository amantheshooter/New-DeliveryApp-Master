package co.location.locationapp.di.modules

import android.app.Application
import androidx.room.Room
import co.location.locationapp.data.source.local.Database
import co.location.locationapp.data.source.local.LocationDataDao
import co.location.locationapp.utils.Constants
import co.location.locationapp.utils.OpenForTesting
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(app: Application): Database = Room.databaseBuilder(app,
            Database::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideLocationDao(
            database: Database): LocationDataDao = database.locationDataDao()
}