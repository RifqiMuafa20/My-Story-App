package com.d121211063.mystoryapp.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.d121211063.mystoryapp.R
import com.d121211063.mystoryapp.data.preference.UserModel
import com.d121211063.mystoryapp.databinding.ActivityLoginBinding
import com.d121211063.mystoryapp.ui.ViewModelFactory
import com.d121211063.mystoryapp.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            Log.d("LoginActivity", "Email: $email, Password: $password")

            when {
                email.isEmpty() -> {
                    binding.edLoginEmail.error = "Email is Empty"
                }
                password.isEmpty() -> {
                    binding.edLoginPassword.error = "Password is Empty"
                }
                else -> {
                    viewModel.login(email, password)
                }
            }

            viewModel.isError.observe(this) { isError ->
                if (isError) {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.saveSession(viewModel.userToken.value!!)

                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}