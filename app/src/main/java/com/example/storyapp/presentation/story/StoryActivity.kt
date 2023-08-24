package com.example.storyapp.presentation.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storyapp.R
import android.provider.Settings
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.databinding.ActivityStoryBinding
import com.example.storyapp.presentation.Locator.Locator
import com.example.storyapp.presentation.adapter.LoadingStateAdapter
import com.example.storyapp.presentation.adapter.StoryAdapter
import com.example.storyapp.presentation.addstory.AddStoryActivity
import com.example.storyapp.presentation.login.LoginActivity
import com.example.storyapp.presentation.map.MapsActivity
import com.example.storyapp.presentation.utils.launchAndCollectIn

class StoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<StoryViewModel>(factoryProducer = { Locator.storyViewModelFactory })
    private val adapter by lazy { StoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAdapter()
        viewModel.storyState.launchAndCollectIn(this) { state ->
            state.resultStories?.let { adapter.submitData(lifecycle, it) }
            state.username?.let { binding.userName.text = it }
        }

        binding.addStoryFab.setOnClickListener {
            startActivity(Intent(this@StoryActivity, AddStoryActivity::class.java))
        }
        binding.logoutAction.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
            finish()
        }
        binding.languageAction.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        binding.mapAction.setOnClickListener {
            startActivity(Intent(this@StoryActivity, MapsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStories()
        viewModel.getUser()
    }

    private fun initAdapter() {
        binding.storyList.apply {
            adapter = this@StoryActivity.adapter.withLoadStateFooter(
                footer = LoadingStateAdapter { this@StoryActivity.adapter.retry() }
            )
            layoutManager = LinearLayoutManager(this@StoryActivity)
        }
    }
}