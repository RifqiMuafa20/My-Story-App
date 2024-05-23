package com.d121211063.mystoryapp.data.preference

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
