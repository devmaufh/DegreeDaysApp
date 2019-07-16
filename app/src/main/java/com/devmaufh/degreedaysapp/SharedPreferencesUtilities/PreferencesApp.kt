package com.devmaufh.degreedaysapp.SharedPreferencesUtilities

import android.app.Application

class PreferencesApp :Application(){
    companion object{
        lateinit var  prefs:UserPreferences
    }

    override fun onCreate() {
        super.onCreate()
        prefs=UserPreferences(applicationContext)
    }
}