package com.example.storyapp.presentation.domain.usecase

import com.example.storyapp.presentation.domain.contract.LoginUseCaseContract
import com.example.storyapp.presentation.domain.entity.UserEntity
import com.example.storyapp.presentation.domain.interfaces.AuthRepository
import com.example.storyapp.presentation.domain.interfaces.UserPreferenceRepository
import com.example.storyapp.presentation.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val authRepository: AuthRepository,
) : LoginUseCaseContract {
    override operator fun invoke(email: String, password: String): Flow<ResultState<String>> =
        flow {
            emit(ResultState.Loading())
            authRepository.login(email, password).catch {
                emit(ResultState.Error(it.message.toString()))
            }.collect { result ->
                if (result.error) {
                    emit(ResultState.Error(result.message))
                } else {
                    result.loginResult.let {
                        userPreferenceRepository.saveUser(
                            UserEntity(
                                it.userId, it.name, it.token
                            )
                        )
                    }
                    emit(ResultState.Success(result.message))
                }
            }
        }
}