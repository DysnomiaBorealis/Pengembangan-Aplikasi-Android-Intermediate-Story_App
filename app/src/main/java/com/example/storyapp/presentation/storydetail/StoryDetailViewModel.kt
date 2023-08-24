package com.example.storyapp.presentation.storydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.storyapp.presentation.domain.contract.GetStoryDetailUseCaseContract
import com.example.storyapp.presentation.domain.usecase.GetStoryDetailUseCase
import kotlinx.coroutines.flow.*


class StoryDetailViewModel(private val getStoryDetailUseCase: GetStoryDetailUseCaseContract) :
    ViewModel() {
    private val _storyDetailViewState = MutableStateFlow(StoryDetailViewState())
    val storyDetailViewState
        get() = _storyDetailViewState.asStateFlow()

    fun getStoryDetail(id: String) {
        getStoryDetailUseCase(id).onEach { result ->
            _storyDetailViewState.update {
                it.copy(resultStory = result)
            }
        }.launchIn(viewModelScope)
    }

    class Factory(
        private val getStoryDetailUseCase: GetStoryDetailUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoryDetailViewModel::class.java)) {
                return StoryDetailViewModel(getStoryDetailUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}