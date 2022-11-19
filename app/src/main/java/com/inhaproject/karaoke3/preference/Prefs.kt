package com.inhaproject.karaoke3.preference

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    private val prefNm="mPref"
    private val prefs=context.getSharedPreferences(prefNm,Context.MODE_PRIVATE)

    var token:String?
        get() = prefs.getString("token",null)
        set(value) {
            prefs.edit().putString("token",value).apply()
        }


}