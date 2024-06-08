package com.d121211063.mystoryapp.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.d121211063.mystoryapp.databinding.ActivitySplashScreenBinding
import com.d121211063.mystoryapp.ui.ViewModelFactory
import com.d121211063.mystoryapp.ui.main.MainActivity
import com.d121211063.mystoryapp.ui.welcome.WelcomeActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: SplashScreenViewModel
    private val time: Long = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[SplashScreenViewModel::class.java]

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }, time)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, time)
            }
        }

        supportActionBar?.hide()

        binding.progressBar.visibility = View.VISIBLE

        binding.progressBar.isIndeterminate = true
    }
}