package com.ssverma.covidtracker.util

import java.util.*

object CoreUtil {

    fun getLocale(): Locale {
        return Locale.getDefault()
    }

    fun getCountryIso2(): String {
        return getLocale().country
    }

}