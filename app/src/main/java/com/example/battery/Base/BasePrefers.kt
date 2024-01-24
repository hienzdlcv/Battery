package com.example.battery.Base

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.example.battery.UI.Setup.SetupSetting
import com.google.gson.Gson

private const val PREF_LANGUAGE = "pref_language"
class BasePrefers(context: Context) {

    private val prefsLocale = "prefsLocale"
    private val prefsReceive = "prefsReceive"
    private val prefsSetup = "setup_pref"

    private val mPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    var locale
        get() = mPrefs.getString(prefsLocale, "en")
        set(value) = mPrefs.edit { putString(prefsLocale, value) }

    var receiver
        get() = mPrefs.getBoolean(prefsReceive, true)
        set(value) = mPrefs.edit { putBoolean(prefsReceive, value) }

    var setupSetting
        get() = mPrefs.getString(prefsSetup, Gson().toJson(SetupSetting()))
        set(value) = mPrefs.edit { putString(prefsSetup, value) }

    companion object {
        @Volatile
        private var INSTANCE: BasePrefers? = null

        fun initPrefs(context: Context): BasePrefers {
            return INSTANCE ?: synchronized(this) {
                val instance = BasePrefers(context)
                INSTANCE = instance
                // return instance
                instance
            }
        }

        fun getPrefsInstance(): BasePrefers {
            return INSTANCE ?: error("GoPreferences not initialized!")
        }
    }
}