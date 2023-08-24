package com.example.storyapp.presentation.domain.usecase

import com.example.storyapp.presentation.domain.contract.RegisterUseCaseContract
import com.example.storyapp.presentation.data.responses.GeneralResponse
import com.example.storyapp.presentation.domain.interfaces.AuthRepository
import com.example.storyapp.presentation.utils.ResultState
import kotlinx.coroutines.flow.*
import android.util.Log
import retrofit2.HttpException

class RegisterUseCase(private val authRepository: AuthRepository) : RegisterUseCaseContract {

    override fun invoke(name: String, email: String, password: String): Flow<ResultState<String>> =
        flow {
            val trimmedEmail = email.trim()
            Log.d("RegisterUseCase", "Trimmed email: $trimmedEmail")
            Log.d("RegisterUseCase", "Register attempt started for email: $trimmedEmail")
            emit(ResultState.Loading())
            try {
                val response = authRepository.register(trimmedEmail, password , name)
                if (response.error) {
                    Log.d("RegisterUseCase", "Server responded with error: ${response.message}")
                    emit(ResultState.Error(response.message))
                } else {
                    emit(ResultState.Success(response.message))
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("RegisterUseCase", "Register attempt failed with exception: $errorBody", e)
                emit(ResultState.Error(e.message ?: "Unknown error"))
            } catch (e: Exception) {
                Log.e("RegisterUseCase", "Register attempt failed with exception", e)
                emit(ResultState.Error(e.message ?: "Unknown error"))
            }
        }
}
