package com.example.storyapp.presentation.domain.usecase

import com.example.storyapp.presentation.domain.contract.GetStoriesLocationUseCaseContract
import com.example.storyapp.presentation.domain.entity.StoryEntity
import com.example.storyapp.presentation.domain.interfaces.StoryRepository
import com.example.storyapp.presentation.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect

class GetStoriesLocationUseCase(private val storyRepository: StoryRepository) :
    GetStoriesLocationUseCaseContract {

    override operator fun invoke(): Flow<ResultState<List<StoryEntity>>> = flow {
        emit(ResultState.Loading())
        try {
            storyRepository.getStoriesLocation(1).collect { response ->
                val stories = response.listStory.map { storyResponse ->
                    StoryEntity(
                        id = storyResponse.id,
                        name = storyResponse.name,
                        description = storyResponse.description,
                        photoUrl = storyResponse.photoUrl,
                        lat = storyResponse.lat,
                        lng = storyResponse.lon
                    )
                }
                emit(ResultState.Success(stories))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(message = e.message ?: "Unknown error"))
        }
    }
}
