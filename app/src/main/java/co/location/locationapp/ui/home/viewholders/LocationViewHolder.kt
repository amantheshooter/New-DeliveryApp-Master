package co.location.locationapp.ui.home.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.location.locationapp.databinding.LocationListItemBinding
import co.location.locationapp.data.model.DeliveryLocation

class LocationViewHolder(private val binding: LocationListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: (DeliveryLocation?) -> Unit, deliveryLocationItem: DeliveryLocation?) {
        binding.deliveryLocationItem = deliveryLocationItem
        binding.locationCard.setOnClickListener { clickListener(binding.deliveryLocationItem) }
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): LocationViewHolder {
            val binding = LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LocationViewHolder(binding)
        }
    }
}