package co.location.locationapp.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.location.locationapp.data.source.LocationRepository
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory @Inject constructor(
        private val locationRepository: LocationRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(locationRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}