package com.d121211063.mystoryapp.ui.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.d121211063.mystoryapp.databinding.ActivityRegisterBinding
import com.d121211063.mystoryapp.ui.ViewModelFactory
import com.d121211063.mystoryapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.signupButton.setOnClickListener {
            val email = binding.edRegisterEmail.text.toString()
            val name = binding.edRegisterName.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            Log.d("RegisterActivity", "Email: $email, Name: $name, Password: $password")

            when {
                email.isEmpty() -> {
                    binding.edRegisterEmail.error = "Email is Empty"
                }
                name.isEmpty() -> {
                    binding.edRegisterName.error = "Name is Empty"
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = "Password is Empty"
                }
                else -> {
                    viewModel.register(name, email, password)
                }
            }
        }

        viewModel.isError.observe(this) {isError ->
            if (isError) {
                Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}