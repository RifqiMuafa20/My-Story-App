package com.d121211063.mystoryapp.di

import android.content.Context
import com.d121211063.mystoryapp.data.UserRepository
import com.d121211063.mystoryapp.data.preference.UserPreference
import com.d121211063.mystoryapp.data.preference.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}