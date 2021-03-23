package de.smf.setup
import android.app.Application
import android.util.Log
import io.sentry.Sentry
import io.sentry.android.AndroidSentryClientFactory
import io.sentry.event.Breadcrumb
import io.sentry.event.BreadcrumbBuilder
import timber.log.Timber

object SmfSentry {
    fun setup(
        application: Application,
        sentryDSN: String,
        buildType: String
    ) {

        Sentry.init(
                sentryDSN,
                AndroidSentryClientFactory(application)
        )

        Sentry.getStoredClient().addBuilderHelper {
            val pInfo = application.packageManager.getPackageInfo(application.packageName, 0)
            it.withEnvironment(buildType)
                    .withRelease(pInfo.versionName + "-" + pInfo.versionCode)
        }

        Timber.plant(SentryLogTree())
    }

    @JvmStatic
    fun capture(e: Throwable) {
        Sentry.capture(e)
    }

    private class SentryLogTree : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            val level: Breadcrumb.Level = when (priority) {
                Log.ASSERT -> Breadcrumb.Level.CRITICAL
                Log.DEBUG -> Breadcrumb.Level.DEBUG
                Log.INFO -> Breadcrumb.Level.INFO
                Log.VERBOSE -> Breadcrumb.Level.INFO
                Log.WARN -> Breadcrumb.Level.WARNING
                Log.ERROR -> Breadcrumb.Level.ERROR
                else -> Breadcrumb.Level.INFO
            }
            Sentry.getContext().recordBreadcrumb(
                    BreadcrumbBuilder()
                            .setMessage("$tag: $message")
                            .setLevel(level)
                            .build()
            )
        }
    }

    fun setTag(tagKey: String, value: String) {
        // addTag will do a put on the map
        Sentry.getContext().addTag(tagKey, value)
    }
}
