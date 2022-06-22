package com.homework.food.utils

import android.content.Context
import android.content.SharedPreferences
import com.homework.food.utils.Constants.Companion.MODE_PRIVATE
import com.homework.food.utils.Constants.Companion.MY_PREFS
import com.homework.food.utils.Constants.Companion.PREFS_KEY

class Prefs(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(MY_PREFS,MODE_PRIVATE)

    fun savePrefs(){
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(PREFS_KEY, false)
        editor.commit()
    }

    fun getPrefs(): Boolean {
        return preferences.getBoolean(PREFS_KEY,true)
    }

}