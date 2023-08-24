package com.example.storyapp.presentation.adapter

import com.example.storyapp.presentation.domain.entity.StoryEntity
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ItemStoryBinding
import com.example.storyapp.presentation.data.responses.StoryResponse
import com.example.storyapp.presentation.storydetail.StoryDetailActivity

class StoryAdapter : PagingDataAdapter<StoryEntity, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {


    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoryEntity) {
            binding.tvItemName.text = item.name
            binding.tvItemDesc.text = item.description
            Glide.with(binding.root.context).load(item.photoUrl).into(binding.ivItemPhoto)
            binding.root.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        binding.root.context as Activity,
                        Pair(binding.ivItemPhoto, "photo"),
                        Pair(binding.tvItemName, "name"),
                        Pair(binding.tvItemDesc, "description"),
                    )

                binding.root.context.startActivity(
                    Intent(
                        binding.root.context, StoryDetailActivity::class.java
                    ).putExtra(StoryDetailActivity.EXTRA_STORY_ID, item.id),
                    optionsCompat.toBundle()
                )
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }


}