package com.example.storyapp.presentation.login

import com.example.storyapp.presentation.utils.ResultState


data class LoginViewState(
    val resultVerifyUser: ResultState<String> = ResultState.Idle()
)