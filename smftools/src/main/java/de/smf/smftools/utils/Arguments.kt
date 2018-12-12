package de.smf.smftools.utils

import android.os.Bundle
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

/**
 * arguments which can be used to pass values to an activity or fragment
 * use [PaperParcelable] and [PaperParcel] to generate Parcelable implementation
 */
interface Arguments : PaperParcelable {

    fun toBundle(key: String = Arguments.KEY_ARGUMENTS): Bundle {
        val b = Bundle()
        addToBundle(b, key)
        return b
    }

    fun addToBundle(b: Bundle, key: String = Arguments.KEY_ARGUMENTS) {
        b.putParcelable(key, this)
        b.putString(key + "." + Arguments.KEY_TYPE, this.javaClass.canonicalName)
    }

    companion object {

        const val KEY_ARGUMENTS = "Arguments"
        const val KEY_TYPE = "Type"

        fun <T : Arguments> fromBundle(b: Bundle): T {
            return fromBundle(b, KEY_ARGUMENTS)
        }

        fun <T : Arguments> fromBundle(b: Bundle, key: String): T {
            b.classLoader = Arguments::class.java.classLoader
            return b.getParcelable<T>(key)
        }

        fun <T : Arguments> fromBundleIfPresent(b: Bundle?, key: String): T? {
            if (b == null) {
                return null
            }
            b.classLoader = Arguments::class.java.classLoader
            return b.getParcelable<T>(key)
        }

        @JvmOverloads fun removeArguments(b: Bundle?, key: String = KEY_ARGUMENTS) {
            if (b == null) {
                return
            }
            b.remove(key)
        }
    }

}
