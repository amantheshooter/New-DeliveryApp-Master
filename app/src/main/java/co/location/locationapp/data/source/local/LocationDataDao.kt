package co.location.locationapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.location.locationapp.data.model.DeliveryLocation
import io.reactivex.Single

@Dao
interface LocationDataDao {

    @Query("SELECT * FROM location limit :limit offset :offset")
    fun queryLocationData(limit: Int, offset: Int): Single<List<DeliveryLocation>>

    @Query("DELETE FROM location")
    fun deleteAllLocationData(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllLocationData(deliveryLocationData: List<DeliveryLocation>)

}