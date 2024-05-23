package com.d121211063.mystoryapp.di

import android.content.Context
import com.d121211063.mystoryapp.data.UserRepository
import com.d121211063.mystoryapp.data.preference.UserPreference
import com.d121211063.mystoryapp.data.preference.dataStore
import com.d121211063.mystoryapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
}