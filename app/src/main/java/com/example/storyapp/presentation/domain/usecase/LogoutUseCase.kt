package com.example.storyapp.presentation.domain.usecase

import com.example.storyapp.presentation.domain.contract.LogoutUseCaseContract
import com.example.storyapp.presentation.domain.interfaces.UserPreferenceRepository

class LogoutUseCase(private val userPreferenceRepository: UserPreferenceRepository) :
    LogoutUseCaseContract {
    override suspend fun invoke() = userPreferenceRepository.clearUser()
}