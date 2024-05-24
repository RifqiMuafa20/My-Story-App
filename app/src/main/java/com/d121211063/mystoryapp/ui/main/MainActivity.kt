package com.d121211063.mystoryapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.d121211063.mystoryapp.R
import com.d121211063.mystoryapp.data.remote.response.ListStoryItem
import com.d121211063.mystoryapp.databinding.ActivityMainBinding
import com.d121211063.mystoryapp.ui.ViewModelFactory
import com.d121211063.mystoryapp.ui.add.AddStoryActivity
import com.d121211063.mystoryapp.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager

//        viewModel.getSession().observe(this) { session ->
//            Log.d("MainActivity", "onCreate: $session")
//            if (session.isLogin == false) {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            } else {
//                viewModel.getStories()
//            }
//        }

        viewModel.listStories.observe(this) { stories ->
            setStoryData(stories)
        }

        viewModel.isError.observe(this) { isError ->
            if (isError) {
                viewModel.errorMessage.observe(this) { errorMessage ->
                    Toast.makeText(this, errorMessage ?: R.string.load_data_failed.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setStoryData(listUser: List<ListStoryItem>) {
        val adapter = StoriesAdapter()
        adapter.submitList(listUser)
        binding.rvStories.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_logout -> {
                viewModel.logout()
                startActivity(Intent(this, WelcomeActivity::class.java))
                true
            }
            R.id.action_add -> {
                startActivity(Intent(this, AddStoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}