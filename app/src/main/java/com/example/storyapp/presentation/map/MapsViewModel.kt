package com.example.storyapp.presentation.map

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.storyapp.presentation.domain.contract.GetStoriesLocationUseCaseContract
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.storyapp.presentation.domain.usecase.GetStoriesLocationUseCase

class MapsViewModel(
    private val getStoriesLocationUseCase: GetStoriesLocationUseCaseContract,
) : ViewModel() {
    private val _mapsState = MutableStateFlow(MapsViewState())
    val mapsState = _mapsState.asStateFlow()

    fun getStories() {
        viewModelScope.launch {
            getStoriesLocationUseCase().collect { stories ->
                _mapsState.update {
                    it.copy(resultStories = stories)
                }
            }
        }
    }

    class Factory(
        private val getStoriesLocationUseCase: GetStoriesLocationUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
                return MapsViewModel(getStoriesLocationUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}