package com.example.storyapp.presentation.register

import androidx.lifecycle.*
import com.example.storyapp.presentation.domain.contract.RegisterUseCaseContract
import com.example.storyapp.presentation.domain.usecase.RegisterUseCase
import com.example.storyapp.presentation.utils.ResultState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel(
private val registerUseCase: RegisterUseCaseContract
) : ViewModel() {

    private val _registerViewState = MutableStateFlow(RegisterViewState())
    val registerViewState = _registerViewState.asStateFlow()

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registerViewState.update { it.copy(isLoading = true) }

            registerUseCase(name, email, password).onEach { result ->
                _registerViewState.update {
                    it.copy(resultRegisterUser = result, isLoading = false)
                }
            }.launchIn(viewModelScope)
        }
    }


    class Factory(
        private val registerUseCase: RegisterUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(registerUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}