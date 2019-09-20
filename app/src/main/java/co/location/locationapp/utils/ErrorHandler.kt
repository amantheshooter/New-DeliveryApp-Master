package co.location.locationapp.utils

import android.content.Context
import co.location.locationapp.R
import java.io.IOException

class ErrorHandler {

    companion object {
        fun getErrorMessage(throwable: Throwable, context: Context): String? {
            return if (throwable is IOException) {
                context.resources.getString(R.string.check_internet)
            } else {
                throwable.message
            }
        }
    }
}