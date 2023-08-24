package com.example.storyapp.presentation.domain.usecase

import com.example.storyapp.presentation.domain.contract.GetStoriesUseCaseContract
import com.example.storyapp.presentation.domain.interfaces.StoryRepository
import com.example.storyapp.presentation.domain.mapper.map
import kotlinx.coroutines.flow.map

class GetStoriesUseCase(private val storyRepository: StoryRepository) : GetStoriesUseCaseContract {
    override fun invoke() = storyRepository.getStories().map { pagingData ->
        pagingData.map()
    }

}