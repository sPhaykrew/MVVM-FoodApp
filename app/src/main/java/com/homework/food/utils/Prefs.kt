package com.homework.food.utils

import android.content.Context
import android.content.SharedPreferences
import com.homework.food.utils.Constants.Companion.MODE_PRIVATE
import com.homework.food.utils.Constants.Companion.MY_PREFS
import com.homework.food.utils.Constants.Companion.PREFS_KEY

class Prefs(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(MY_PREFS, MODE_PRIVATE)

    fun savePrefs(value : String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(PREFS_KEY,value)
        editor.commit()
    }

    fun getPrefs(): String {
        return preferences.getString(PREFS_KEY,"byName")!!
    }
}
