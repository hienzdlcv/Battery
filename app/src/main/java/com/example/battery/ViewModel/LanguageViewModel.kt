package com.example.battery.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.battery.Api.ApiFromGifphy.Language

class LanguageViewModel(application: Application) : AndroidViewModel(application) {
    var chosenLanguage: Language? = null
}