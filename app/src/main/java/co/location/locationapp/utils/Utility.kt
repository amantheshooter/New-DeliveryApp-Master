package co.location.locationapp.utils

import co.location.locationapp.R
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class Utility {

    companion object {
        fun getRequestOption(): RequestOptions {
            return RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform()
        }
    }
}