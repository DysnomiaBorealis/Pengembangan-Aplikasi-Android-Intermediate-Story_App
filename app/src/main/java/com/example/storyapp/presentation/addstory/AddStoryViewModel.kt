package com.example.storyapp.presentation.addstory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.storyapp.presentation.domain.contract.AddStoryUseCaseContract
import com.example.storyapp.presentation.domain.usecase.AddStoryUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.*
import java.io.File

class AddStoryViewModel(private val addStoryUseCase: AddStoryUseCaseContract) : ViewModel() {
    private val _addStoryState = MutableStateFlow(AddStoryViewState())
    val addStoryState = _addStoryState.asStateFlow()

    fun addStory(file: File, description: String, latLng: LatLng?) {
        addStoryUseCase(file, description, latLng).onEach { result ->
            _addStoryState.update {
                it.copy(resultAddStory = result)
            }
        }.launchIn(viewModelScope)
    }

    class Factory(
        private val addStoryUseCase: AddStoryUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
                return AddStoryViewModel(addStoryUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}