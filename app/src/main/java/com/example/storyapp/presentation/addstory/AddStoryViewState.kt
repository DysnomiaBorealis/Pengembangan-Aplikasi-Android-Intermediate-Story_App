package com.example.storyapp.presentation.addstory

import com.example.storyapp.presentation.utils.ResultState

data class AddStoryViewState(
    val resultAddStory: ResultState<String> = ResultState.Idle()
)