package com.ssverma.covidtracker.data.model.domain

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil

data class Symptom(
    val title: String,
    val description: String,
    @DrawableRes val illustration: Int
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Symptom>() {
            override fun areItemsTheSame(oldItem: Symptom, newItem: Symptom): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Symptom, newItem: Symptom): Boolean {
                return oldItem == newItem
            }

        }
    }
}