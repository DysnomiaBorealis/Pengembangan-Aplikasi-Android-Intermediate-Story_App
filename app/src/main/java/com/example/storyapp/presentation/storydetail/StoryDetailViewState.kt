package com.example.storyapp.presentation.storydetail

import com.example.storyapp.presentation.domain.entity.StoryEntity
import com.example.storyapp.presentation.utils.ResultState

data class StoryDetailViewState(
    val resultStory: ResultState<StoryEntity> = ResultState.Idle()
)