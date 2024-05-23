package com.d121211063.mystoryapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d121211063.mystoryapp.data.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isError = MutableLiveData<Boolean>()
    val isError: MutableLiveData<Boolean> = _isError
    fun register(name: String, email: String, password: String){
        viewModelScope.launch {
            try {
                val response = repository.register(name, email, password)
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
            }
        }
    }
}