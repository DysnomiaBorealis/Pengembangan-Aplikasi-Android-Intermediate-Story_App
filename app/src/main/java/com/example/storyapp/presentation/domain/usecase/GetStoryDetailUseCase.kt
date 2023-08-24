package com.example.storyapp.presentation.domain.usecase

import com.example.storyapp.presentation.domain.contract.GetStoryDetailUseCaseContract
import com.example.storyapp.presentation.domain.entity.StoryEntity
import com.example.storyapp.presentation.domain.interfaces.StoryRepository
import com.example.storyapp.presentation.domain.mapper.map
import com.example.storyapp.presentation.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetStoryDetailUseCase(private val storyRepository: StoryRepository) :
    GetStoryDetailUseCaseContract {
    override operator fun invoke(id: String): Flow<ResultState<StoryEntity>> = flow {
        emit(ResultState.Loading())
        storyRepository.getStory(id).map {
            it.story.map()
        }.catch {
            emit(ResultState.Error(message = it.message.toString()))
        }.collect {
            emit(ResultState.Success(it))
        }
    }
}