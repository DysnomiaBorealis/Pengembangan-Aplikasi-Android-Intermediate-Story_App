package com.example.storyapp.presentation.domain.contract

import com.example.storyapp.presentation.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface RegisterUseCaseContract {
    operator fun invoke(name: String, email: String, password: String): Flow<ResultState<String>>
}