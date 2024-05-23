package com.d121211063.mystoryapp.ui.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.d121211063.mystoryapp.R
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

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.signupButton.setOnClickListener {
            val email = binding.edRegisterEmail.text.toString()
            val name = binding.edRegisterName.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.edRegisterEmail.error = getString(R.string.email_is_empty)
                }
                name.isEmpty() -> {
                    binding.edRegisterName.error = getString(R.string.name_is_empty)
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = getString(R.string.password_is_empty)
                }
                password.length < 8 -> {
                    binding.edRegisterPassword.error = getString(R.string.error_password)
                }
                else -> {
                    viewModel.register(name, email, password)
                }
            }
        }

        viewModel.isError.observe(this) { isError ->
            if (isError) {
                viewModel.errorMessage.observe(this) { errorMessage ->
                    Toast.makeText(this, errorMessage ?: R.string.register_failed.toString(), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}