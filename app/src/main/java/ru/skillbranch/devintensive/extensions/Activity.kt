package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Float.px(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View?) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = window.decorView
    val visibleBounds = Rect()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = rootView.height - visibleBounds.height()
    val marginOfError :Int = 100F.px()
    return heightDiff > marginOfError
}


fun Activity.isKeyboardClosed(): Boolean = !isKeyboardOpen()