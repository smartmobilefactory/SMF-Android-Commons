package de.smf.smftools.ui

import android.app.Activity
import android.view.ViewGroup

/** An indirection which allows controlling the root container used for each activity.  */
interface AppContainer {
    /** The root [ViewGroup] into which the activity should place its contents.  */
    fun bind(activity: Activity, group: ViewGroup): ViewGroup

    object DEFAULT: AppContainer {
        override fun bind(activity: Activity, group: ViewGroup): ViewGroup {
            return group
        }
    }
}
