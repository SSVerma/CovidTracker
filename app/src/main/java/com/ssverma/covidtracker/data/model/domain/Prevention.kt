package com.ssverma.covidtracker.data.model.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil

data class Prevention(
    @StringRes val title: Int,
    @DrawableRes val imageId: Int
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Prevention>() {
            override fun areItemsTheSame(oldItem: Prevention, newItem: Prevention): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Prevention, newItem: Prevention): Boolean {
                return oldItem == newItem
            }

        }
    }
}