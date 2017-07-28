package de.smf.smftools.report

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View

import net.hockeyapp.android.FeedbackActivity

class CustomFeedbackActivity : FeedbackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResume() {
        super.onResume()
        findViewById<View>(net.hockeyapp.android.R.id.input_email).visibility = View.GONE
    }
}
