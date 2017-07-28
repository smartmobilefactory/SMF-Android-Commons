package de.smf.smftools.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.util.TypedValue
import android.view.LayoutInflater
import de.smf.smftools.utils.extensions.compat.getDrawableCompat


/**
 * unwraps the activity from a context if possible
 */
fun Context.getActivity() : Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

val Context.layoutInflater
        get() = LayoutInflater.from(this)

@ColorInt
fun Context.getAttrColor(@AttrRes id: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    return typedValue.data
}

fun Context.getAttrDrawable(@AttrRes id: Int): Drawable {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val drawableRes = typedValue.resourceId
    return getDrawableCompat(drawableRes)
}
