package com.ssverma.covidtracker.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    private const val API_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    private const val DISPLAY_DATE_FORMAT = "d MMM yyyy"
    private const val API_DATE_FORMAT = "yyyy-MM-dd"
    private const val API_TIME_FORMAT = "HH:mm:ss.SSSXXX"

    private fun getApiDateTimeFormatInstance(): DateFormat {
        return SimpleDateFormat(API_DATE_TIME_FORMAT, Locale.getDefault())
    }

    private fun getDisplayDateFormatInstance(): DateFormat {
        return SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())
    }

    fun getCurrentDate(): Date {
        return Calendar.getInstance().time
    }

    fun apiDateStrToDateObj(apiDate: String?): Date? {
        return apiDate?.let {
            try {
                getApiDateTimeFormatInstance().parse(apiDate)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun displayDateStrToDateObj(displayDate: String?): Date? {
        return displayDate?.let {
            getDisplayDateFormatInstance().parse(displayDate)
        }
    }

    fun dateObjToApiDateStr(date: Date): String {
        return getApiDateTimeFormatInstance().format(date)
    }

    fun dateObjToDisplayDateStr(date: Date): String {
        return getDisplayDateFormatInstance().format(date)
    }

    fun apiDateStrToDisplayDateStr(apiDate: String?): String? {
        return apiDateStrToDateObj(apiDate)?.let { dateObjToDisplayDateStr(it) }
    }

    fun displayDateStrToApiDateStr(displayDate: String): String? {
        return displayDateStrToDateObj(displayDate)?.let { dateObjToApiDateStr(it) }
    }

    fun dateAfterNDays(date: Date, nDays: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, nDays)
        return calendar.time
    }

    fun millisToDate(milliSeconds: Long): Date {
        return Date(milliSeconds)
    }

}