package de.smf.smftools.utils.extensions.compat

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat


fun Drawable.getLayoutDirectionCompat(): Int {
    return DrawableCompat.getLayoutDirection(this)
}

fun Drawable.isAutoMirrorCompat(): Boolean {
    return DrawableCompat.isAutoMirrored(this)
}

fun Drawable.jumpToCurrentStateCompat() {
    DrawableCompat.jumpToCurrentState(this)
}

fun Drawable.setAutoMirrorCompat(mirrored: Boolean) {
    DrawableCompat.setAutoMirrored(this, mirrored)
}

fun Drawable.setHotspotCompat(x: Float, y: Float) {
    DrawableCompat.setHotspot(this, x, y)
}

fun Drawable.setHotspotBoundsCompat(left: Int, top: Int, right: Int, bottom: Int) {
    DrawableCompat.setHotspotBounds(this, left, top, right, bottom)
}

fun Drawable.setLayoutDirectionCompat(layoutDirection: Int) {
    DrawableCompat.setLayoutDirection(this, layoutDirection)
}

fun Drawable.setTintCompat(color: Int) {
    DrawableCompat.setTint(DrawableCompat.wrap(this).mutate(), color)
}

fun Drawable.setTintListCompat(list: ColorStateList) {
    DrawableCompat.setTintList(this, list)
}

fun Drawable.setTintModeCompat(tintMode: PorterDuff.Mode) {
    DrawableCompat.setTintMode(this, tintMode)
}
