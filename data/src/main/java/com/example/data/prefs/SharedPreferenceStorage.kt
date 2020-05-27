package com.example.data.prefs

import android.content.Context
import android.content.SharedPreferences


class SharedPreferenceStorage(context: Context) {
    companion object {
        const val PREFERENCES_NAME = "rebuild_preference"
        const val DEFAULT_VALUE_STRING = ""
        const val DEFAULT_VALUE_BOOLEAN = false
    }

    private val preferences: SharedPreferences? by lazy {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setString(key: String?, value: String?) {
        val editor = preferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun setBoolean(key: String?, value: Boolean) {
        val editor = preferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun getString(key: String?): String {
        return preferences?.getString(key, DEFAULT_VALUE_STRING) ?: ""
    }

    fun getBoolean(key: String?): Boolean {

        return preferences?.getBoolean(key, DEFAULT_VALUE_BOOLEAN) ?: false
    }

    fun removeKey(key: String?) {
        val edit = preferences?.edit()
        edit?.remove(key)
        edit?.apply()
    }

    fun clear() {

        val edit = preferences?.edit()
        edit?.clear()
        edit?.apply()
    }
}