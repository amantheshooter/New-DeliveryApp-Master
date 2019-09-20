package co.location.locationapp.utils

import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.location.locationapp.BuildConfig
import co.location.locationapp.data.model.DeliveryLocation
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context)
            .load(url)
            .apply(Utility.getRequestOption())
            .into(view)
}


@BindingAdapter("mapView")
fun addMarkerZoomToMap(mapView: MapView, deliveryLocation: DeliveryLocation) {
    mapView.onCreate(Bundle())
    mapView.getMapAsync { googleMap ->
        MapsInitializer.initialize(mapView.context)
        val latLng = LatLng(deliveryLocation.address.lat, deliveryLocation.address.lng)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, BuildConfig.GOOGLE_MAP_ZOOM))
        googleMap.addMarker(MarkerOptions().position(latLng).title(deliveryLocation.address.address))
        mapView.onResume()
    }
}
