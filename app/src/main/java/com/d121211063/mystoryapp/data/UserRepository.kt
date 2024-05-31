package com.d121211063.mystoryapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.d121211063.mystoryapp.R
import com.d121211063.mystoryapp.data.data.StoriesPagingSource
import com.d121211063.mystoryapp.data.preference.UserModel
import com.d121211063.mystoryapp.data.preference.UserPreference
import com.d121211063.mystoryapp.data.remote.response.FileUploadResponse
import com.d121211063.mystoryapp.data.remote.response.ListStoryItem
import com.d121211063.mystoryapp.data.remote.response.LoginResponse
import com.d121211063.mystoryapp.data.remote.response.RegisterResponse
import com.d121211063.mystoryapp.data.remote.response.StoriesResponse
import com.d121211063.mystoryapp.data.remote.retrofit.ApiConfig
import com.d121211063.mystoryapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private var apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.logout()
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
            val response = apiService.register(name, email, password)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: R.string.an_unknown_error_occurred.toString())
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(email, password)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: R.string.an_unknown_error_occurred.toString())
        }
    }

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoriesPagingSource(apiService)
            }
        ).liveData
    }

    suspend fun getStoriesLocation(): Result<StoriesResponse> {
        return try {
            val response = apiService.getStoriesWithLocation()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: R.string.an_unknown_error_occurred.toString())
        }
    }

    suspend fun uploadStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null
    ): Result<FileUploadResponse> {
        return try {
            val response = apiService.uploadImage(file, description, lat, lon)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: R.string.an_unknown_error_occurred.toString())
        }
    }

    fun updateToken(token: String) {
        instance?.let {
            it.apiService = ApiConfig.getApiService(token)
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository {
            instance?.let { return it }
            return synchronized(this) {
                val newInstance = UserRepository(userPreference, apiService)
                instance = newInstance
                newInstance
            }
        }
    }
}