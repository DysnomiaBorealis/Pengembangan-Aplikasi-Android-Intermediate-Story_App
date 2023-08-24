package com.example.storyapp.presentation.data.repository
import com.example.storyapp.presentation.data.responses.GeneralResponse
import com.example.storyapp.presentation.data.source.Api.ApiServices
import com.example.storyapp.presentation.domain.entity.UserEntity
import com.example.storyapp.presentation.domain.interfaces.AuthRepository
import com.example.storyapp.presentation.domain.interfaces.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class AuthRepositoryImplement (    private val api: ApiServices,
                                   private val userPreferenceRepository: UserPreferenceRepository
) : AuthRepository {

    override suspend fun register(email: String, password: String, name: String): GeneralResponse {
        return api.register(
            hashMapOf(
                Pair("name", name),
                Pair("password", password),
                Pair("email", email),
            )
        )
    }


    override fun login(email: String, password: String) = flow {
        val loginResponse = api.login(
            hashMapOf(
                Pair("password", password),
                Pair("email", email),
            )
        )

        // save user's data if login is successful
        if (!loginResponse.error) {
            val userEntity = UserEntity(
                id = loginResponse.loginResult.userId,
                name = loginResponse.loginResult.name,
                token = loginResponse.loginResult.token
            )
            userPreferenceRepository.saveUser(userEntity)
        }

        emit(loginResponse)
    }.flowOn(Dispatchers.IO)


}