package com.devmaufh.degreedaysapp.SharedPreferencesUtilities

import android.content.Context
import android.content.SharedPreferences

class UserPreferences constructor(context: Context) {
    val PREFS_NAME="com.devmau.preferences"
    val USERNAME="user_name"
    val LOG_STATUS="is_logged"
    val PASSWORD="user_pass"

    val prefs:SharedPreferences = context.getSharedPreferences(PREFS_NAME,0)

    var name:String
        get() = prefs.getString(USERNAME,"")
        set(value)=prefs.edit().putString(USERNAME,value).apply()

    var logStatus:String
        get() = prefs.getString(LOG_STATUS,"")
        set(value) = prefs.edit().putString(LOG_STATUS,value).apply()

    var password:String
        get() = prefs.getString(PASSWORD,"")
        set(value) = prefs.edit().putString(PASSWORD,value).apply()
}