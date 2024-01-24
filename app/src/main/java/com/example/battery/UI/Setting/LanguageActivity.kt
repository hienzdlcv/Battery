package com.example.battery.UI.Setting

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.battery.Base.BaseActivity
import com.example.battery.Base.BasePrefers
import com.example.battery.MainActivity
import com.example.battery.R
import com.example.battery.UI.Gallery.Crud
import com.example.battery.UI.Setting.farmentforViewPager.LanguageUtils
import com.example.battery.ViewModel.LanguageViewModel
import com.example.battery.databinding.ActivityLanguageBinding
import kotlinx.coroutines.launch
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
                Crud.language.value = "en"
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
                Crud.language.value = "vi"
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
                Crud.language.value = "ja"
                checkbox3.visibility = View.GONE
                checkbox1.visibility = View.VISIBLE
                checkbox2.visibility = View.VISIBLE
                checkbox35.visibility = View.VISIBLE
                checkbox15.visibility = View.GONE
                checkbox25.visibility = View.GONE
            }
            checkbox35.setOnClickListener {
                Log.d("11111", "onStart1: ${ Crud.language.value} ")
                checkbox3.visibility = View.VISIBLE
                checkbox35.visibility = View.GONE
            }
            choseBtn.setOnClickListener {
                lifecycleScope.launch {
                    Crud.language.flowWithLifecycle(lifecycle).collect{
                        Log.d("11111", "onStart2: ${it} ")
                        when(it) {
                            "en"-> changeLanguage("en")
                            "vi"-> changeLanguage("vi")
                            "ja" -> changeLanguage("ja")
                        }
                    }
                }
            }
        }
    }

    private fun changeLanguage(languageCode: String) {
        LanguageUtils.updateLanguage(this, languageCode)
        Toast.makeText(this@LanguageActivity,"Success, reopen app to apply change",Toast.LENGTH_SHORT).show()
        onBackPressed()
    }
    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        Runtime.getRuntime().exit(0)
    }
}