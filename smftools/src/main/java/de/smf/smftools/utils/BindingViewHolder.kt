package de.smf.smftools.utils

import android.app.Activity
import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import de.smf.smftools.utils.extensions.getActivity

/**
 * ViewHolder which holds an ViewDataBinding
 */
class BindingViewHolder<out T : ViewDataBinding>(
        val binding: T
) : RecyclerView.ViewHolder(binding.root) {

    val context: Context
        get() = binding.root.context

    val activity: Activity?
        get() = context.getActivity()
}