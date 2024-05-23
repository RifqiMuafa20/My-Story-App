package com.d121211063.mystoryapp.data

import com.d121211063.mystoryapp.data.preference.UserModel
import com.d121211063.mystoryapp.data.preference.UserPreference
import com.d121211063.mystoryapp.data.remote.response.LoginResponse
import com.d121211063.mystoryapp.data.remote.response.RegisterResponse
import com.d121211063.mystoryapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.await

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService : ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(name: String, email: String, password: String) : RegisterResponse {
        return apiService.register(name, email, password).await()
    }

    suspend fun login(email: String, password: String) : LoginResponse {
        return apiService.login(email, password).await()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}