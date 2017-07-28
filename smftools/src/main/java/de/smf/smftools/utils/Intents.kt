package de.smf.smftools.utils

import android.content.Context
import android.content.Intent

/**
 * Attempt to launch the supplied [Intent]. Queries on-device packages before launching and
 * will display a simple message if none are available to handle it.
 */
fun Context.maybeStartActivity(intent: Intent?): Boolean{
    if (intent == null){
        return false
    }
    if (intent.hasHandler(this)) {
        startActivity(intent)
        return true
    }
    return false
}

/**
 * Attempt to launch Android's chooser for the supplied [Intent]. Queries on-device
 * packages before launching and will display a simple message if none are available to handle
 * it.
 */
fun Context.maybeStartChooser(intent: Intent?): Boolean {
    if (intent == null){
        return false
    }
    if (intent.hasHandler(this)) {
        startActivity(Intent.createChooser(intent, null))
        return true
    }
    return false
}


/**
 * Queries on-device packages for a handler for the supplied [Intent].
 */
fun Intent.hasHandler(context: Context): Boolean {
    val handlers = context.packageManager.queryIntentActivities(this, 0)
    return handlers != null && !handlers.isEmpty()
}
