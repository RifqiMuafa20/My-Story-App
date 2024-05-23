package com.d121211063.mystoryapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.d121211063.mystoryapp.R
import com.d121211063.mystoryapp.data.remote.response.ListStoryItem
import com.d121211063.mystoryapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra(EXTRA_IMAGE)
        val name = intent.getStringExtra(EXTRA_NAME).toString()
        val description = intent.getStringExtra(EXTRA_DESCRIPTION).toString()
        val latitude = intent.getDoubleExtra(EXTRA_LATITUDE, 0.0).toDouble()
        val longitude = intent.getDoubleExtra(EXTRA_LONGITUDE, 0.0).toDouble()
        val date = intent.getStringExtra(EXTRA_DATE).toString()

        getDetailStory(ListStoryItem(image, date, name, description, longitude, id = "1", latitude))
    }

    private fun getDetailStory(detail: ListStoryItem) {
        binding.tvItemName.text = detail.name
        binding.storyDescription.text = detail.description
        binding.storyDate.text = detail.createdAt
        Glide.with(this@DetailActivity)
            .load(detail.photoUrl)
            .into(binding.imgItemPhoto)
    }

    companion object {
        var EXTRA_IMAGE = "extra_image"
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
        var EXTRA_LATITUDE = "extra_latitude"
        var EXTRA_LONGITUDE = "extra_longitude"
        var EXTRA_DATE = "extra_date"
    }
}