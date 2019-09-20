package co.location.locationapp.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.location.locationapp.R
import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.databinding.ActivityHomeBinding
import co.location.locationapp.ui.home.adapter.HomeAdapter
import co.location.locationapp.ui.home.enums.State
import co.location.locationapp.ui.home.viewmodel.HomeViewModel
import co.location.locationapp.ui.home.viewmodel.HomeViewModelFactory
import co.location.locationapp.ui.map.MapsActivity
import co.location.locationapp.utils.Constants
import co.location.locationapp.utils.ErrorHandler
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.header.view.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var locationsViewModel: HomeViewModel
    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        AndroidInjection.inject(this)
        setSupportActionBar(binding.toolbar.toolbar)
        locationsViewModel = ViewModelProviders.of(this, homeViewModelFactory).get(
                HomeViewModel::class.java)
        initAdapter()
        observeList()
        initState()
        initError()
        Log.d("data -- > ",locationsViewModel.toString())
    }


    private fun initAdapter() {
        homeAdapter = HomeAdapter { deliveryLocation: DeliveryLocation? -> locationItemClicked(deliveryLocation) }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
            adapter = homeAdapter
            itemAnimator = null
        }
        swipeRefreshLayout.setOnRefreshListener {
            hideSnackBar()
            locationsViewModel.loadRefreshData()
        }

    }

    private fun observeList() {
        locationsViewModel.deliveryLocationList.observe(this, Observer {
            homeAdapter.submitList(it)
        })
    }


    private fun initState() {
        locationsViewModel.getStateObserver().observe(this, Observer { state ->
            if (swipeRefreshLayout.isRefreshing && state == State.DONE) {
                swipeRefreshLayout.isRefreshing = false
            } else {
                if (state == State.NO_DATA) {
                    showSnackBarWithNoAction(resources.getString(R.string.no_data_found))
                }
                val isListEmpty = locationsViewModel.listIsEmpty()
                progressBar.visibility = if (isListEmpty && state == State.LOADING) View.VISIBLE else View.GONE
                if (!isListEmpty && !swipeRefreshLayout.isRefreshing) homeAdapter.setState(state
                        ?: State.DONE)
            }
        })
    }

    private fun initError() {
        locationsViewModel.getErrorObserver().observe(this, Observer {
            it?.let {
                progressBar.visibility = View.GONE
                val errorMessage = ErrorHandler.getErrorMessage(it, this@HomeActivity).toString()
                if (swipeRefreshLayout.isRefreshing) showSnackBarWithNoAction(errorMessage)
                else showSnackBarWithAction(errorMessage)
            }
        })
    }

    private fun locationItemClicked(deliveryLocationItem: DeliveryLocation?) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra(Constants.OBJECT, deliveryLocationItem)
        startActivity(intent)
    }

    private fun hideSnackBar() {
        snackBar?.let { if (it.isShown) it.dismiss() }
    }

    private fun showSnackBarWithAction(message: String) {
        var length = Snackbar.LENGTH_INDEFINITE
        if (swipeRefreshLayout.isRefreshing) {
            length = Snackbar.LENGTH_LONG
        }
        snackBar = Snackbar
                .make(relativeLayout, message, length)
                .setAction(resources.getString(R.string.retry)) {
                    if (!locationsViewModel.listIsEmpty()) {
                        homeAdapter.setState(State.LOADING)
                        recyclerView.scrollToPosition(homeAdapter.itemCount - 1)
                    }
                    locationsViewModel.retry()
                }
                .setActionTextColor(Color.WHITE)
        snackBar?.show()
    }

    private fun showSnackBarWithNoAction(message: String) {
        snackBar = Snackbar
                .make(relativeLayout, message, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE)
        snackBar?.show()
    }
}

