package com.example.battery.UI.Gallery

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow

object Crud {
    val edit = MutableStateFlow(false)
    val chooseAll = MutableStateFlow(false)


    val hideme = MutableStateFlow(false)
    val hide2screen = MutableStateFlow(false)

    val language = MutableStateFlow("en")

    private const val PREF_NAME = "MyAppPreferences"
    private const val PREF_LANGUAGE = "pref_language"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveLanguage(context: Context, languageCode: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(PREF_LANGUAGE, languageCode)
        editor.apply()
    }

    fun getSavedLanguage(context: Context): String {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(PREF_LANGUAGE, "en") ?: "en"
    }

}