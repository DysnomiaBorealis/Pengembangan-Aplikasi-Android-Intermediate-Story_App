package com.example.storyapp.presentation.map

import com.example.storyapp.presentation.domain.entity.StoryEntity
import com.example.storyapp.presentation.utils.ResultState

data class MapsViewState(
    val resultStories: ResultState<List<StoryEntity>> = ResultState.Idle(),
)