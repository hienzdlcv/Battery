package com.example.battery.UI.Setting

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.battery.Base.BaseActivity
import com.example.battery.Base.BasePrefers
import com.example.battery.Constants.Constants
import com.example.battery.R
import com.example.battery.ViewModel.LanguageViewModel
import com.example.battery.databinding.ActivityLanguageBinding
import java.util.Locale

class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_language

    private var newLocale: String? = null
    private lateinit var languageViewModel: LanguageViewModel

    override fun setupUI() {
        super.setupUI()
        languageViewModel = ViewModelProvider(this@LanguageActivity).get(LanguageViewModel::class.java)
    }

    override fun setupListener() {
        super.setupListener()

        binding.apply {
            /*checkbox2.setOnClickListener {
                setLanguage("vi")
            }*/
            icBack.setOnClickListener {
                onBackPressed()
            }
            checkbox1.setOnClickListener {
                checkbox1.visibility = View.GONE
                checkbox15.visibility = View.VISIBLE
                checkbox25.visibility = View.GONE
                checkbox35.visibility = View.GONE
                checkbox2.visibility = View.VISIBLE
                checkbox3.visibility = View.VISIBLE
            }
            checkbox15.setOnClickListener {
                checkbox1.visibility = View.VISIBLE
                checkbox15.visibility = View.GONE
            }
            checkbox2.setOnClickListener {
                checkbox2.visibility = View.GONE
                checkbox25.visibility = View.VISIBLE
                checkbox15.visibility = View.GONE
                checkbox35.visibility = View.GONE
                checkbox1.visibility = View.VISIBLE
                checkbox3.visibility = View.VISIBLE
            }
            checkbox25.setOnClickListener {
                checkbox2.visibility = View.VISIBLE
                checkbox25.visibility = View.GONE
            }
            checkbox3.setOnClickListener {
                checkbox3.visibility = View.GONE
                checkbox1.visibility = View.VISIBLE
                checkbox2.visibility = View.VISIBLE
                checkbox35.visibility = View.VISIBLE
                checkbox15.visibility = View.GONE
                checkbox25.visibility = View.GONE
            }
            checkbox35.setOnClickListener {
                checkbox3.visibility = View.VISIBLE
                checkbox35.visibility = View.GONE
            }
            choseBtn.setOnClickListener {
                newLocale = languageViewModel.chosenLanguage?.code
                val locale = BasePrefers.getPrefsInstance().locale
                if (locale != newLocale) {
                    BasePrefers.getPrefsInstance().locale = newLocale ?: Constants.en
                }
                refreshLayout()
            }
        }
    }

    fun setLanguage(language: String) {
        val resources: Resources = resources
        val configuration: Configuration = resources.configuration
        val locale = Locale(language)

        configuration.setLocale(locale)

        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun refreshLayout() {
        val intent = this@LanguageActivity.intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        this@LanguageActivity.finish()
        startActivity(intent)
    }
}