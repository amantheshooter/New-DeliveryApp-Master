package co.location.locationapp.ui.home.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.location.locationapp.data.model.DeliveryLocation
import co.location.locationapp.ui.home.enums.State
import co.location.locationapp.ui.home.viewholders.LocationViewHolder
import co.location.locationapp.ui.home.viewholders.ProgressBarViewHolder

const val DATA_VIEW_TYPE = 1
const val FOOTER_VIEW_TYPE = 2

typealias LocationItemClickListener = (DeliveryLocation?) -> Unit

class HomeAdapter(private val clickListener: LocationItemClickListener)
    : PagedListAdapter<DeliveryLocation, RecyclerView.ViewHolder>(LocationDiffCallback) {

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            if (viewType == DATA_VIEW_TYPE) LocationViewHolder.create(parent) else ProgressBarViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as LocationViewHolder).bind(clickListener, getItem(position))
        else (holder as ProgressBarViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int) =
            if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE


    companion object {
        val LocationDiffCallback = object : DiffUtil.ItemCallback<DeliveryLocation>() {
            override fun areItemsTheSame(oldItem: DeliveryLocation, newItem: DeliveryLocation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DeliveryLocation, newItem: DeliveryLocation): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount() = super.getItemCount() + if (hasFooter()) 1 else 0


    private fun hasFooter() = super.getItemCount() != 0 && (state == State.LOADING)

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

}
