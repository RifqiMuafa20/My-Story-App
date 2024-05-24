package com.d121211063.mystoryapp.data

import com.d121211063.mystoryapp.R
import com.d121211063.mystoryapp.data.preference.UserModel
import com.d121211063.mystoryapp.data.preference.UserPreference
import com.d121211063.mystoryapp.data.remote.response.FileUploadResponse
import com.d121211063.mystoryapp.data.remote.response.LoginResponse
import com.d121211063.mystoryapp.data.remote.response.RegisterResponse
import com.d121211063.mystoryapp.data.remote.response.StoriesResponse
import com.d121211063.mystoryapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun register(name: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = apiService.register(name, email, password).await()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: R.string.an_unknown_error_occurred.toString())
        }
    }

    suspend fun login(email: String, password: String) : Result<LoginResponse> {
        return try {
            val response = apiService.login(email, password).await()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: R.string.an_unknown_error_occurred.toString())
        }
    }

    suspend fun getStories() : Result<StoriesResponse> {
        return try {
            val response = apiService.getStories().await()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: R.string.an_unknown_error_occurred.toString())
        }
    }

    suspend fun uploadStory(file: MultipartBody.Part, description: RequestBody) : Result<FileUploadResponse> {
        return try {
            val response = apiService.uploadImage(file, description)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: R.string.an_unknown_error_occurred.toString())
        }
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