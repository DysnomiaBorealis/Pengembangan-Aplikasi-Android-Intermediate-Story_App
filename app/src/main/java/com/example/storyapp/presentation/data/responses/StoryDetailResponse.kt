package com.example.storyapp.presentation.data.responses

data class StoryDetailResponse(
    val error: Boolean,
    val message: String,
    val story: StoryResponse
)