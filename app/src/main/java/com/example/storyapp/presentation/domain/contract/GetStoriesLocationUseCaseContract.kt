package com.example.storyapp.presentation.domain.contract

import com.example.storyapp.presentation.domain.entity.StoryEntity
import com.example.storyapp.presentation.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface GetStoriesLocationUseCaseContract {
    operator fun invoke(): Flow<ResultState<List<StoryEntity>>>
}