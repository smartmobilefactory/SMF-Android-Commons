package de.smf.setup

import android.app.Application
import android.util.Log
import io.sentry.Breadcrumb
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid
import timber.log.Timber

object SmfSentry {
    fun setup(
        application: Application,
        sentryDSN: String,
        buildType: String
    ) {

        SentryAndroid.init(application) { options ->
            options.dsn = sentryDSN
            val pInfo = application.packageManager.getPackageInfo(application.packageName, 0)
            options.environment = buildType
            @Suppress("DEPRECATION")
            options.release = pInfo.versionName + "-" + pInfo.versionCode
        }

        Timber.plant(SentryLogTree())
    }

    @JvmStatic
    fun capture(e: Throwable) {
        Sentry.captureException(e)
    }

    private class SentryLogTree : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            val level: SentryLevel = when (priority) {
                Log.ASSERT -> SentryLevel.FATAL
                Log.DEBUG -> SentryLevel.DEBUG
                Log.INFO -> SentryLevel.INFO
                Log.VERBOSE -> SentryLevel.INFO
                Log.WARN -> SentryLevel.WARNING
                Log.ERROR -> SentryLevel.ERROR
                else -> SentryLevel.INFO
            }
            Sentry.addBreadcrumb(Breadcrumb().apply {
                setMessage("$tag: $message")
                setLevel(level)
            })
        }
    }
}