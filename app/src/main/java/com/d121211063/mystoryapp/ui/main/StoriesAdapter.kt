package com.d121211063.mystoryapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d121211063.mystoryapp.data.remote.response.ListStoryItem
import com.d121211063.mystoryapp.databinding.ItemStoriesBinding
import com.d121211063.mystoryapp.ui.detail.DetailActivity
import com.d121211063.mystoryapp.util.DateTime

class StoriesAdapter : ListAdapter<ListStoryItem, StoriesAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesAdapter.MyViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoriesAdapter.MyViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)

        holder.itemView.setOnClickListener {
            val date = story.createdAt?.let { DateTime.getDate(it) }
            val data = ListStoryItem(story.photoUrl, date, story.name, story.description, story.lon, story.id, story.lat)

            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_STORY, data)

            it.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ItemStoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem){
            val date = story.createdAt?.let { DateTime.getDate(it) }

            binding.tvItemName.text = story.name
            binding.storyDescription.text = story.description
            binding.storyDate.text = date
            Glide.with(binding.root)
                .load(story.photoUrl)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}