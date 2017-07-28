package de.smf.smftools.report

import android.app.Activity
import android.net.Uri

import com.mattprecious.telescope.Lens

import net.hockeyapp.android.FeedbackManager

import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class BugReportLens : Lens() {
    private var context: Activity? = null

    fun setActivity(activity: Activity): BugReportLens {
        this.context = activity
        return this
    }

    override fun onCapture(screenshot: File?) {
        val log = saveLog()
        val screenShotUri = Uri.fromFile(screenshot)

        val uris: Array<Uri>
        if (log != null) {
            uris = arrayOf(screenShotUri, log)
        } else {
            uris = arrayOf(screenShotUri)
        }

        FeedbackManager.showFeedbackActivity(context, *uris)
    }

    private fun saveLog(): Uri? {

        var lineSeparator = System.getProperty("line.separator")
        if (lineSeparator.isBlank()) {
            lineSeparator = "\n"
        }

        var outputStream: OutputStreamWriter? = null
        try {

            val file = File(context!!.cacheDir, System.currentTimeMillis().toString() + ".log")

            outputStream = OutputStreamWriter(FileOutputStream(file))

            val e = Runtime.getRuntime().exec(arrayOf("logcat", "-d", "*:D"))
            val bufferedReader = BufferedReader(InputStreamReader(e.inputStream))

            while (true) {
                val line = bufferedReader.readLine()
                if (line == null) {
                    bufferedReader.close()
                    break
                }
                outputStream.write(line)
                outputStream.write(lineSeparator)
            }
            outputStream.close()
            outputStream = null

            return Uri.fromFile(file)
        } catch (var7: IOException) {
            var7.printStackTrace()
            return null
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

}
