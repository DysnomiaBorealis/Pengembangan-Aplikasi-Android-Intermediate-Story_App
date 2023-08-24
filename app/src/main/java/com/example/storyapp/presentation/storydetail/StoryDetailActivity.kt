package com.example.storyapp.presentation.storydetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityStoryDetailBinding
import com.example.storyapp.presentation.Locator.Locator
import com.example.storyapp.presentation.domain.entity.StoryEntity
import com.example.storyapp.presentation.utils.ResultState
import com.example.storyapp.presentation.utils.launchAndCollectIn


class StoryDetailActivity : AppCompatActivity() {

    private lateinit var activityStoryDetailBinding: ActivityStoryDetailBinding
    private val storyDetailViewModel: StoryDetailViewModel by viewModels { Locator.storyDetailViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityStoryDetailBinding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(activityStoryDetailBinding.root)

        val storyId = intent.getStringExtra(EXTRA_STORY_ID) ?: return
        storyDetailViewModel.getStoryDetail(storyId)

        storyDetailViewModel.storyDetailViewState.launchAndCollectIn(this) { viewState ->
            handleViewState(viewState)
        }
    }

    private fun handleViewState(viewState: StoryDetailViewState) {
        when (val storyResult = viewState.resultStory) {
            is ResultState.Loading -> {
                activityStoryDetailBinding.progressBar.visibility = View.VISIBLE
            }
            is ResultState.Success -> {
                activityStoryDetailBinding.progressBar.visibility = View.GONE
                storyResult.data?.let { story ->
                    updateUIWithStory(story)
                }
            }
            is ResultState.Error -> {
                activityStoryDetailBinding.progressBar.visibility = View.GONE
                Toast.makeText(this, storyResult.message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    private fun updateUIWithStory(story: StoryEntity) {
        activityStoryDetailBinding.apply {
            tvDetailName.text = story.name
            tvDetailDescription.text = story.description
            Glide.with(this@StoryDetailActivity)
                .load(story.photoUrl)
                .into(ivDetailPhoto)
        }
    }

    companion object {
        const val EXTRA_STORY_ID = "EXTRA_STORY_ID"
    }
}
