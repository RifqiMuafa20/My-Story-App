package com.d121211063.mystoryapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d121211063.mystoryapp.data.UserRepository
import com.d121211063.mystoryapp.data.preference.UserModel
import com.d121211063.mystoryapp.data.remote.response.LoginResponse
import com.d121211063.mystoryapp.data.remote.response.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isError = MutableLiveData<Boolean>()
    val isError: MutableLiveData<Boolean> = _isError

    private val _userToken = MutableLiveData<UserModel?>()
    val userToken: MutableLiveData<UserModel?> = _userToken

    fun saveSession(user : UserModel) {
        viewModelScope.launch {
            try {
                val response = repository.saveSession(user)
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            }
        }
    }
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                val name : String = response.loginResult.name
                val token : String = response.loginResult.token

                _userToken.value = UserModel(name, token)
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            }
        }
    }

}