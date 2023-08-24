package com.example.storyapp.presentation.domain.usecase

import com.example.storyapp.presentation.domain.contract.GetUserUseCaseContract
import com.example.storyapp.presentation.domain.entity.UserEntity
import com.example.storyapp.presentation.domain.interfaces.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(private val userPreferenceRepository: UserPreferenceRepository) :
    GetUserUseCaseContract {
    override fun invoke(): Flow<UserEntity> = userPreferenceRepository.userData
}