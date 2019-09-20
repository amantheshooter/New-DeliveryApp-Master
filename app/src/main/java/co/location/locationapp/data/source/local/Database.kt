package co.location.locationapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.location.locationapp.data.model.DeliveryLocation

@Database(entities = [DeliveryLocation::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun locationDataDao(): LocationDataDao
}