package de.smf.smftools.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * ViewHolder which holds an ViewDataBinding
 */
class BindingViewHolder<out T : ViewDataBinding>(
        val binding: T
) : RecyclerView.ViewHolder(binding.root) {

    val context: Context
        get() = binding.root.context

    val activity: Activity?
        get() {
            var context = context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }
}