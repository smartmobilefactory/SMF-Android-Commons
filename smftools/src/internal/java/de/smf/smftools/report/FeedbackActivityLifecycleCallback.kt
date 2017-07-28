package de.smf.smftools.report

import android.app.Activity

import net.hockeyapp.android.FeedbackActivity
import net.hockeyapp.android.FeedbackManager
import net.hockeyapp.android.FeedbackManagerListener
import net.hockeyapp.android.objects.FeedbackMessage
import net.hockeyapp.android.objects.FeedbackUserDataElement
import net.hockeyapp.android.utils.PrefsUtil

import de.smf.smftools.utils.EmptyActivityLifecycleCallbacks
import de.timfreiheit.hockey.utils.HockeyLifecycleHelper

class FeedbackActivityLifecycleCallback : EmptyActivityLifecycleCallbacks() {

    override fun onActivityResumed(activity: Activity) {
        super.onActivityResumed(activity)

        if (HockeyLifecycleHelper.getInstance() == null) {
            return
        }

        PrefsUtil.getInstance().saveNameEmailSubjectToPrefs(activity, null, null, null)
        PrefsUtil.getInstance().saveFeedbackTokenToPrefs(activity, null)
        FeedbackManager.setRequireUserEmail(FeedbackUserDataElement.DONT_SHOW)
        FeedbackManager.register(activity, HockeyLifecycleHelper.getInstance().config.hockeyAppId, object : FeedbackManagerListener() {

            override fun getFeedbackActivityClass(): Class<out FeedbackActivity> {
                return CustomFeedbackActivity::class.java
            }

            override fun feedbackAnswered(latestMessage: FeedbackMessage): Boolean {
                return true
            }

        })
    }

    override fun onActivityPaused(activity: Activity) {
        super.onActivityPaused(activity)

        if (HockeyLifecycleHelper.getInstance() == null) {
            return
        }

        FeedbackManager.unregister()
    }
}
