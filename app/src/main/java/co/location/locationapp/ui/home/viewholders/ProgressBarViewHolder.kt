package co.location.locationapp.ui.home.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.location.locationapp.R
import co.location.locationapp.ui.home.enums.State
import kotlinx.android.synthetic.main.item_list_footer.view.*

class ProgressBarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        itemView.progress_bar.visibility = if (status == State.LOADING) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(parent: ViewGroup): ProgressBarViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_footer, parent, false)
            return ProgressBarViewHolder(view)
        }
    }
}