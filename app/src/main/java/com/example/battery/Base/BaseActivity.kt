package com.example.battery.Base

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.battery.Constants.Constants
import com.example.battery.Constants.ContextUtils
import java.util.Locale

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding()
        setupData()
        setupUI()
        setupListener()
    }

    protected lateinit var binding: T
    protected abstract val layoutId: Int

    protected open fun binding() {
        binding = createBinding()
    }

    private fun createBinding(): T {
        return DataBindingUtil.setContentView(this, layoutId)
    }

    protected open fun setupUI() {}

    protected open fun setupData() {}

    protected open fun setupListener() {}

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let { ctx ->
            val preferences = BasePrefers.initPrefs(ctx)
            val locale = preferences.locale ?: Constants.en

            locale.let {
                val locale1 = Locale.forLanguageTag(it)
                val localeUpdatedContext = ContextUtils.updateLocale(ctx, locale1)
                super.attachBaseContext(localeUpdatedContext)
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    open fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}