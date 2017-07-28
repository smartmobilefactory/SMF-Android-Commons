package de.smf.smftools.appcontainer

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.mattprecious.telescope.Lens
import com.mattprecious.telescope.TelescopeLayout

import java.io.File

import de.smf.smftools.R
import de.smf.smftools.report.BugReportLens
import de.smf.smftools.ui.AppContainer

class TelescopeAppContainer : AppContainer {

    private val lens = BugReportLens()

    lateinit var telescopeLayout: TelescopeLayout

    override fun bind(activity: Activity, group: ViewGroup): ViewGroup {
        activity.layoutInflater.inflate(R.layout.debug_appcontainer_telescope, group)

        telescopeLayout = group.findViewById<TelescopeLayout>(R.id.telescope_container)
        telescopeLayout.setPointerCount(POINT_COUNT)
        lens.setActivity(activity)
        telescopeLayout.setLens(lens)
        // If you have not seen the telescope dialog before, show it.
        if (!DebugPrefs.seenTelescopeDialog(activity)) {
            telescopeLayout.postDelayed(Runnable {
                if (activity.isFinishing) {
                    return@Runnable
                }

                DebugPrefs.setTelescopeDialogSeen(activity, true)
                showTelescopeDialog(activity)
            }, 1000)

        }

        return telescopeLayout
    }

    fun showTelescopeDialog(activity: Activity) {
        val inflater = LayoutInflater.from(activity)
        val content = inflater.inflate(R.layout.debug_telescope_tutorial_dialog, null) as TelescopeLayout
        content.setPointerCount(POINT_COUNT)

        val hintText = content.findViewById<TextView>(R.id.hintText)
        hintText.text = Html.fromHtml("Found a bug? Report it!<br><br>Press and hold <b>three</b> fingers on the screen to launch the reporting dialog.")

        val dialog = AlertDialog.Builder(activity).setView(content).setCancelable(false).create()
        content.setLens(object : Lens() {
            override fun onCapture(screenshot: File?) {
                dialog.dismiss()

                val toastContext = ContextThemeWrapper(activity, android.R.style.Theme_DeviceDefault_Dialog)
                val toastInflater = LayoutInflater.from(toastContext)
                val toast = Toast.makeText(toastContext, "", Toast.LENGTH_SHORT)
                val toastView = toastInflater.inflate(R.layout.debug_telescope_tutorial_toast, null)
                toast.view = toastView
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        })

        content.findViewById<View>(R.id.close).setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    companion object {

        private val POINT_COUNT = 3
    }
}
