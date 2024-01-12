package com.example.travel_taipei.util

import android.content.Context
import android.content.SharedPreferences

private var sharedPreferences: SharedPreferences? = null

private fun openPref(context: Context) {
    sharedPreferences = context.getSharedPreferences("PREF_FILE", Context.MODE_PRIVATE)
}

private fun closePref() {
    sharedPreferences = null
}

fun getPrefStringValue(context: Context, key: String, defaultValue: String): String? {
    openPref(context)
    val result = sharedPreferences!!.getString(key, defaultValue)
    closePref()
    return result
}

fun setPrefStringValue(context: Context, key: String, value: String) {
    openPref(context)
    val prefsPrivateEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
    prefsPrivateEditor!!.putString(key, value)
    prefsPrivateEditor.apply()
    closePref()
}

fun setPrefIntValue(context: Context, key: String, value: Int) {
    openPref(context)
    val prefsPrivateEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
    prefsPrivateEditor!!.putInt(key, value)
    prefsPrivateEditor.apply()
    closePref()
}

fun getPrefIntValue(context: Context, key: String, defaultValue: Int): Int {
    openPref(context)
    val result = sharedPreferences!!.getInt(key, defaultValue)
    closePref()
    return result
}

fun removePrefValue(context: Context, key: String) {
    openPref(context)
    val prefsPrivateEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
    prefsPrivateEditor!!.remove(key)
    prefsPrivateEditor.apply()
    closePref()
}