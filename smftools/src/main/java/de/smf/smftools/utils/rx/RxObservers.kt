package de.smf.smftools.utils.rx


import android.support.annotation.CheckResult
import de.timfreiheit.hockey.utils.WarningExceptionHandler
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.CancellationException

fun Disposable.addTo(composite: CompositeDisposable) {
    composite.add(this)
}

object RxObservers {

    /**
     * per default the [WarningExceptionHandler] is used
     * it will ignore all [IOException] per default
     */
    var crashReporter: ((throwable: Throwable, info: String) -> Unit)? = { throwable, _ ->
        if (throwable !is IOException) {
            // do not report IOExceptions.
            // this are mostly expected exceptions when doing networking
            WarningExceptionHandler.saveException(throwable)
        }
    }

    fun logLifeCycleEvents(from: Any, lifecycle: Observable<*>) {
        val name = from.javaClass.simpleName + "#" + from.hashCode()
        lifecycle.subscribe({ event -> Timber.tag(name).d("LifeCycle: %s", event) }) { error -> Timber.e(error, "LifeCycle error (%s)", name) }
    }

    /**
     * Observer which ignores all events
     */
    @CheckResult
    fun <T> ignore(): LogObserver<T> {
        return observer(null, false)
    }

    /**
     * Observer which logs all events
     */
    @CheckResult
    fun <T> log(): LogObserver<T> {
        return observer("", false)
    }

    /**
     * Observer which ignores all events with a specific tag
     */
    @CheckResult
    fun <T> log(tag: String): LogObserver<T> {
        return observer(tag, false)
    }

    /**
     * Observer which ignores all events and reports errors
     */
    @CheckResult
    fun <T> logAndReport(): LogObserver<T> {
        return observer("", true)
    }

    /**
     * Observer which ignores all events with a specific tag and reports errors
     */
    @CheckResult
    fun <T> logAndReport(tag: String): LogObserver<T> {
        return observer(tag, true)
    }

    /**
     * Observer which only reports errors
     */
    @CheckResult
    fun <T> report(): LogObserver<T> {
        return observer(null, true)
    }

    @CheckResult
    private fun <T> observer(tag: String?, report: Boolean): LogObserver<T> {

        // save caller to improve stacktrace
        // !! make sure the caller is always the second last method !!
        val element = Throwable()
                .stackTrace[2]

        return LogObserver(tag, element, report)
    }

    internal fun reportError(throwable: Throwable, info: String) {
        Timber.e(throwable, "reportError, info: %s", info)
        crashReporter?.invoke(throwable, info)
    }

    class LogObserver<T> internal constructor(private val tag: String?, private val element: StackTraceElement, private val report: Boolean) : DisposableObserver<T>(), SingleObserver<T>, MaybeObserver<T>, CompletableObserver {
        private val callerInfo: String = element.fileName + ":" + element.lineNumber

        override fun onStart() {
            super.onStart()
            log(EVENT_ON_START)
        }

        override fun onNext(value: T) {
            log(EVENT_ON_NEXT, value)
        }

        override fun onSuccess(value: T) {
            log(EVENT_ON_SUCCESS)
        }

        override fun onError(e: Throwable) {
            var error = e
            if (error is CancellationException) {
                return
            }
            error = WrappedException(element, error)
            if (report) {
                reportError(error, tag ?: "")
            } else {
                // always log error at least
                Timber.e(error, "logError, info: %s", tag)
            }
        }

        override fun onComplete() {
            log(EVENT_ON_COMPLETE)
        }

        private fun log(event: String, value: T? = null) {
            if (tag == null) {
                return
            }

            val tree = if (tag.isEmpty())
                Timber.asTree()
            else
                Timber.tag(tag)

            if (value == null) {
                tree.v(LOG_PATTERN_EVENT, callerInfo, event)
            } else {
                tree.v(LOG_PATTERN_EVENT_WITH_ITEM, callerInfo, event, value)
            }

        }

        companion object {

            private val LOG_PATTERN_EVENT = "(%s) %s"
            private val LOG_PATTERN_EVENT_WITH_ITEM = "(%s) %s: %s"
            private val EVENT_ON_START = "onStart"
            private val EVENT_ON_NEXT = "onNext"
            private val EVENT_ON_SUCCESS = "onSuccess"
            private val EVENT_ON_COMPLETE = "onComplete"
        }

    }

    private class WrappedException internal constructor(caller: StackTraceElement, private val wrapped: Throwable) : Exception(wrapped) {

        init {
            val stackTrace = arrayOf(caller)
            setStackTrace(stackTrace)
        }

        override fun toString(): String {
            return wrapped.toString()
        }

    }

}
