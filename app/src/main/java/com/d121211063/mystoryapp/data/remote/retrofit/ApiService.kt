package com.d121211063.mystoryapp.data.remote.retrofit

import com.d121211063.mystoryapp.data.remote.response.LoginResponse
import com.d121211063.mystoryapp.data.remote.response.RegisterResponse
import com.d121211063.mystoryapp.data.remote.response.StoriesResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories() : Call<StoriesResponse>
}