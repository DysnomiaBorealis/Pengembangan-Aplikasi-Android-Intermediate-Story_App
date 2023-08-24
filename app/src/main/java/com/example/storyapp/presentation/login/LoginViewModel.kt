package com.example.storyapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.storyapp.presentation.domain.contract.LoginUseCaseContract
import com.example.storyapp.presentation.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.*


class LoginViewModel(
    private val loginUseCase: LoginUseCaseContract
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState = _loginState.asStateFlow()


    fun doLogin(email: String, password: String) {
        loginUseCase(email, password)
            .onEach { result ->
                _loginState.update {
                    it.copy(resultVerifyUser = result)
                }
            }.launchIn(viewModelScope)
    }

    class Factory(
        private val loginUseCase: LoginUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(loginUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }

}

