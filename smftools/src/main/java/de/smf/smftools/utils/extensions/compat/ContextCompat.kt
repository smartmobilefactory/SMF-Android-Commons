package de.smf.smftools.utils.extensions.compat

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import java.io.File


fun Context.checkSelfPermissionCompat(permission: String): Int {
    return ContextCompat.checkSelfPermission(this, permission)
}

fun Context.getColorCompat(resourceId: Int): Int {
    return ContextCompat.getColor(this, resourceId)
}

fun Context.getColorStateListCompat(resourceId: Int): ColorStateList {
    return ContextCompat.getColorStateList(this, resourceId);
}

fun Context.getDrawableCompat(resourceId: Int): Drawable {
    return ContextCompat.getDrawable(this, resourceId)
}

fun Context.getExternalCacheDirsCompat(): Array<out File>? {
    return ContextCompat.getExternalCacheDirs(this)
}

fun Context.getExternalFilesDirsCompat(type: String): Array<out File> {
    return ContextCompat.getExternalFilesDirs(this, type)
}

fun Context.getObbDirsCompat(): Array<out File> {
    return ContextCompat.getObbDirs(this)
}

fun Context.startActivitiesCompat(intents: Array<Intent>): Boolean {
    return ContextCompat.startActivities(this, intents)
}

fun Context.startActivitiesCompat(intents: Array<Intent>, options: Bundle): Boolean {
    return ContextCompat.startActivities(this, intents, options)
}
