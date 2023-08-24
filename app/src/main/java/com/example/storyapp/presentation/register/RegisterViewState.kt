package com.example.storyapp.presentation.register

import com.example.storyapp.presentation.utils.ResultState

data class RegisterViewState(
    val resultRegisterUser: ResultState<String> = ResultState.Idle(),
    val isLoading: Boolean = false
)
