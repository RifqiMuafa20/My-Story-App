package com.d121211063.mystoryapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.d121211063.mystoryapp.data.UserRepository
import kotlinx.coroutines.launch
import com.d121211063.mystoryapp.data.Result
import com.d121211063.mystoryapp.data.preference.UserModel
import com.d121211063.mystoryapp.data.remote.response.ListStoryItem

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _listStories = MutableLiveData<List<ListStoryItem>>()
    val listStories: LiveData<List<ListStoryItem>> = _listStories

//    init {
//        getStories()
//    }
    fun getStories(){
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.getStories()
            _isLoading.value = false

            when (response) {
                is Result.Success -> {
                    _listStories.value = response.data.listStory
                    _isError.value = false
                    _errorMessage.value = null
                }
                is Result.Error -> {
                    _isError.value = true
                    _errorMessage.value = response.error
                }
                is Result.Loading -> {
                    _isError.value = false
                    _errorMessage.value = null
                    _isLoading.value = true
                }
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}