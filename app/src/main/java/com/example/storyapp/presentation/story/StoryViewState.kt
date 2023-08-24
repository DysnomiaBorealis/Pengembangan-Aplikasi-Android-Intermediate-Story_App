package com.example.storyapp.presentation.story

import androidx.paging.PagingData
import com.example.storyapp.presentation.domain.entity.StoryEntity

data class StoryViewState(
    val resultStories: PagingData<StoryEntity> = PagingData.empty(),
    val username: String = "",
)