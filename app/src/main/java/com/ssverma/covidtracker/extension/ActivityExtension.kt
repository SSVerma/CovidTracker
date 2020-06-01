package com.ssverma.covidtracker.extension

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar


fun Activity.displayToast(@StringRes messageResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, messageResId, duration).show()
}

fun Activity.displayToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.displaySnackBar(
    @StringRes messageResId: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
    @StringRes actionTextResId: Int = 0,
    onActionClick: ((View) -> Unit)? = null
) {
    val content = findViewById<View>(android.R.id.content)
    val snackBar = Snackbar.make(content, messageResId, duration)
    if (actionTextResId != 0) {
        snackBar.setAction(actionTextResId, onActionClick)
    }
    snackBar.show()
}

fun Activity.displaySnackBar(
    message: String?,
    duration: Int = Snackbar.LENGTH_SHORT,
    @StringRes actionTextResId: Int = 0,
    onActionClick: ((View) -> Unit)? = null
) {
    val content = findViewById<View>(android.R.id.content)
    val snackBar = Snackbar.make(content, message ?: "", duration)
    if (actionTextResId != 0) {
        snackBar.setAction(actionTextResId, onActionClick)
    }
    snackBar.show()
}

fun AppCompatActivity.setUpToolbar(
    toolbar: Toolbar,
    title: String? = null,
    shouldShowBackArrow: Boolean = true,
    @DrawableRes customBackIconRes: Int = 0
) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(shouldShowBackArrow)
    supportActionBar?.title = title
    if (customBackIconRes != 0) {
        toolbar.setNavigationIcon(customBackIconRes)
    }
}

fun AppCompatActivity.updateStatusBarColor(@ColorInt color: Int) {
    val window: Window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color
}