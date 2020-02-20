package de.smf.setup

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import com.microsoft.appcenter.distribute.UpdateTrack

object SmfAppCenter {

    private lateinit var distributionConfig: DistributionConfig

    fun setup(
        application: Application,
        appId: String,
        distributionConfig: DistributionConfig
    ): Boolean {
        if (appId.isEmpty()) {
            return false
        }
        this.distributionConfig = distributionConfig

        AppCenter.configure(application, appId)
        if (!Crashes.isEnabled().get()) {
            AppCenter.start(Crashes::class.java)
        }

        application.registerActivityLifecycleCallbacks(ServiceStartCheck())
        return true
    }

    private fun startDistributionService() {
        if (distributionConfig.inAppUpdates && !Distribute.isEnabled().get()) {
            Distribute.setUpdateTrack(UpdateTrack.PUBLIC)
            AppCenter.start(Distribute::class.java)
            Distribute.setEnabledForDebuggableBuild(true)
        }
    }

    data class DistributionConfig(
        val isUpdateActivity: (activity: Activity) -> Boolean = { true },
        val inAppUpdates: Boolean = false
    )

    internal class ServiceStartCheck() : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (distributionConfig.isUpdateActivity(activity)) {
                startDistributionService()
            }
        }

        override fun onActivityStarted(activity: Activity) {}

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}
    }
}
