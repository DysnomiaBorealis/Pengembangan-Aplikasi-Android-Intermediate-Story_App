package com.example.storyapp.presentation.story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.presentation.domain.contract.GetStoriesUseCaseContract
import com.example.storyapp.presentation.domain.contract.GetUserUseCaseContract
import com.example.storyapp.presentation.domain.contract.LogoutUseCaseContract
import com.example.storyapp.presentation.domain.entity.StoryEntity
import com.example.storyapp.presentation.domain.entity.UserEntity
import com.example.storyapp.presentation.domain.interfaces.UserPreferenceRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class StoryState {
    object Idle : StoryState()
    data class StoriesLoaded(val stories: PagingData<StoryEntity>) : StoryState()
    data class UserLoaded(val user: UserEntity) : StoryState()
    object NotLoggedIn : StoryState()
}



class StoryViewModel(
    private val getStoriesUseCase: GetStoriesUseCaseContract,
    private val getUserUseCase: GetUserUseCaseContract,
    private val logoutUseCase: LogoutUseCaseContract,
    private val userPreferenceRepository: UserPreferenceRepository
) : ViewModel() {

    private val _storyState = MutableStateFlow(StoryViewState())
    val storyState = _storyState.asStateFlow()

/*   init {
        viewModelScope.launch {
            val user = userPreferenceRepository.userData.first()
            if (user.id.isEmpty() || user.token.isEmpty()) {
                _storyState.value = StoryState.NotLoggedIn
            }
        }
    } */


    fun getStories() {
        viewModelScope.launch {
            getStoriesUseCase().cachedIn(viewModelScope).collect { stories ->
                _storyState.update {
                    it.copy(resultStories = stories)
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            getUserUseCase().collect { user ->
                _storyState.update {
                    it.copy(username = user.name)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    class Factory(
        private val getStoriesUseCase: GetStoriesUseCaseContract,
        private val getUserUseCase: GetUserUseCaseContract,
        private val logoutUseCase: LogoutUseCaseContract,
        private val userPreferenceRepository: UserPreferenceRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
                return StoryViewModel(
                    getStoriesUseCase,
                    getUserUseCase,
                    logoutUseCase,
                    userPreferenceRepository
                ) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }

}
