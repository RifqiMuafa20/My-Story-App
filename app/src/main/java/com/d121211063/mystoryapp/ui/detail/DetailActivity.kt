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

        val storyItem = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY)

        getDetailStory(storyItem!!)
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
        var EXTRA_STORY = "extra_story"
    }
}