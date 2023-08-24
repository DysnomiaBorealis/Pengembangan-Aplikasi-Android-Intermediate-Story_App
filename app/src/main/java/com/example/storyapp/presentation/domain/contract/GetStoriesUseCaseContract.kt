package com.example.storyapp.presentation.domain.contract

import androidx.paging.PagingData
import com.example.storyapp.presentation.domain.entity.StoryEntity
import kotlinx.coroutines.flow.Flow


interface GetStoriesUseCaseContract {
    operator fun invoke(): Flow<PagingData<StoryEntity>>
}