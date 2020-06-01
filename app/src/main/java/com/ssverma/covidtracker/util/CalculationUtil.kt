package com.ssverma.covidtracker.util

import java.text.DecimalFormat

object CalculationUtil {

    fun roundDecimal(number: Float): Float {
        var numberStr = number.toString()
        var endIndex = numberStr.indexOf("E")
        endIndex = if (endIndex == -1) numberStr.length else endIndex
        numberStr = numberStr.substring(0, endIndex)
        val formatter = DecimalFormat("0.00")
        return formatter.format(numberStr.toFloat()).toBigDecimal().toFloat()
    }

}