package de.smf.setup

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute

object SmfAppCenter {
    fun setup(
        application: Application,
        appId: String
    ) {
        AppCenter.start(application, appId, Distribute::class.java, Crashes::class.java)
    }
}
