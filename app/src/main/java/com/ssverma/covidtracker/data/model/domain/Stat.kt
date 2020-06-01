package com.ssverma.covidtracker.data.model.domain

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import com.ssverma.covidtracker.R
import com.ssverma.covidtracker.data.model.remote.RemoteCovidStat

data class Stat(
    val count: Long,
    val displayCount: String,
    @StringRes val labelResId: Int,
    @ColorRes val backgroundColor: Int
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Stat>() {
            override fun areItemsTheSame(oldItem: Stat, newItem: Stat): Boolean {
                return oldItem.count == newItem.count
            }

            override fun areContentsTheSame(oldItem: Stat, newItem: Stat): Boolean {
                return oldItem == newItem
            }

        }
    }
}


fun RemoteCovidStat.asStatItems(): List<Stat> {
    return listOf(
        /*Total cases*/
        Stat(
            count = cases,
            displayCount = String.format("%,d", cases),
            labelResId = R.string.total_cases,
            backgroundColor = android.R.color.black
        ),
        /*Total active*/
        Stat(
            count = active.toLong(),
            displayCount = String.format("%,d", active),
            labelResId = R.string.total_active,
            backgroundColor = R.color.color_blue_800
        ),
        /*Total recovered*/
        Stat(
            count = recovered.toLong(),
            displayCount = String.format("%,d", recovered),
            labelResId = R.string.total_recovered,
            backgroundColor = R.color.color_green_500
        ),
        /*Total critical*/
        Stat(
            count = critical.toLong(),
            displayCount = String.format("%,d", critical),
            labelResId = R.string.total_critical,
            backgroundColor = R.color.color_orange_500
        ),
        /*Total deaths*/
        Stat(
            count = deaths.toLong(),
            displayCount = String.format("%,d", deaths),
            labelResId = R.string.total_deaths,
            backgroundColor = R.color.color_red_500
        ),
        /*Total test*/
        Stat(
            count = tests,
            displayCount = String.format("%,d", tests),
            labelResId = R.string.total_tests,
            backgroundColor = R.color.color_purple_500
        ),
        /*Today cases*/
        Stat(
            count = todayCases.toLong(),
            displayCount = String.format("%,d", todayCases),
            labelResId = R.string.today_cases,
            backgroundColor = android.R.color.black
        ),
        /*Today deaths*/
        Stat(
            count = todayDeaths.toLong(),
            displayCount = String.format("%,d", todayDeaths),
            labelResId = R.string.today_deaths,
            backgroundColor = R.color.color_red_500
        )
    )
}