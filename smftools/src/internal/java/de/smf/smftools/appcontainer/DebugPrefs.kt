package de.smf.smftools.appcontainer

import android.content.Context
import android.content.SharedPreferences

internal object DebugPrefs {

    private var debugPrefs: SharedPreferences? = null

    fun getDebugSharedPreferences(context: Context): SharedPreferences {
        if (debugPrefs == null) {
            val newPrefs = context.getSharedPreferences("smftools.debug", Context.MODE_PRIVATE)
            debugPrefs = newPrefs
            return newPrefs
        }
        return debugPrefs!!
    }

    fun seenTelescopeDialog(context: Context): Boolean {
        return getDebugSharedPreferences(context).getBoolean("seen_telescope_dialog", false)
    }

    fun setTelescopeDialogSeen(context: Context, seen: Boolean) {
        getDebugSharedPreferences(context).edit().putBoolean("seen_telescope_dialog", seen).apply()
    }

}
