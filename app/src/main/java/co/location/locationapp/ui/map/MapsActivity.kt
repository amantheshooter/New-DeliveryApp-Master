package co.location.locationapp.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.location.locationapp.R
import co.location.locationapp.databinding.ActivityMapsBinding
import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.utils.Constants
import kotlinx.android.synthetic.main.header.view.*


class MapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMapsBinding = DataBindingUtil.setContentView(this, R.layout.activity_maps)
        setSupportActionBar(binding.toolbar.toolbar)

        val locationModal = intent.getParcelableExtra(Constants.OBJECT) as DeliveryLocation
        binding.deliveryLocationItem= locationModal

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
