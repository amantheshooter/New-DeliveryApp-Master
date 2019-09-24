package co.location.locationapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(
        tableName = "location"
)
@Parcelize
data class DeliveryLocation (

        @Json(name = "id")
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

        @Json(name = "description")
        @ColumnInfo(name = "description")
        val name: String?,

        @Json(name = "imageUrl")
        @ColumnInfo(name = "imageUrl")
        val imageUrl: String,

        @Json(name = "location")
        @Embedded
        val address: Address
) : Parcelable
