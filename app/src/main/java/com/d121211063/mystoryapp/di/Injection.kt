package com.d121211063.mystoryapp.di

import android.content.Context
import com.d121211063.mystoryapp.data.UserRepository
import com.d121211063.mystoryapp.data.preference.UserPreference
import com.d121211063.mystoryapp.data.preference.dataStore
import com.d121211063.mystoryapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}