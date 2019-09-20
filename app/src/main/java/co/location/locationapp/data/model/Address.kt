package co.location.locationapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

//error showing due to Parcelize, Its a bug of android studio

@Parcelize
data class Address(
        @Json(name = "lat")
        @ColumnInfo(name = "lat")
        var lat: Double,
        @Json(name = "lng")
        @ColumnInfo(name = "lng")
        var lng: Double,
        @Json(name = "address")
        @ColumnInfo(name = "address")
        var address: String
) : Parcelable