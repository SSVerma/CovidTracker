package com.ssverma.covidtracker.util

import android.app.Activity
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager

object ViewUtil {

    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.currentFocus ?: return
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getBulletPointSymbol() = "\u2022"

}