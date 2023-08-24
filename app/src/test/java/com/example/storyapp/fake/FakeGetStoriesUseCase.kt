package com.example.storyapp.fake

import androidx.paging.PagingData
import com.example.storyapp.presentation.domain.contract.GetStoriesUseCaseContract
import com.example.storyapp.presentation.domain.entity.StoryEntity
import com.example.storyapp.utils.FakeFlowDelegate
import kotlinx.coroutines.flow.Flow


class FakeGetStoriesUseCase : GetStoriesUseCaseContract {

    val fakeDelegate = FakeFlowDelegate<PagingData<StoryEntity>>()

    override fun invoke(): Flow<PagingData<StoryEntity>> = fakeDelegate.flow }
