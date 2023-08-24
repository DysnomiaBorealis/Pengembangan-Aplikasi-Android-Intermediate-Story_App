package com.example.storyapp.presentation.data.responses

data class StoryListResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryResponse>
)