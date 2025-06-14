package com.example.clientserverpaa.net

import com.example.clientserverpaa.utilities.Course
import com.example.clientserverpaa.utilities.LoginRequest
import com.example.clientserverpaa.utilities.RegisterRequest
import com.example.clientserverpaa.utilities.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JsonPlaceholderApi {
    @GET("posts")
    fun getCourses(): Call<List<Course>>
}