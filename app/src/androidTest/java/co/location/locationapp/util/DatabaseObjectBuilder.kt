package co.location.locationapp.util

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import co.location.locationapp.data.source.local.Database
import co.location.locationapp.data.source.local.LocationDataDao

open class DatabaseObjectBuilder {

    protected lateinit var locationDataDao: LocationDataDao
    protected lateinit var database: Database

    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
                context, Database::class.java).build()
        database.clearAllTables()
        locationDataDao = database.locationDataDao()
    }

    fun closeDb() {
        locationDataDao.deleteAllLocationData()
        database.close()
    }

}