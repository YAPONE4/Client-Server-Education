package com.example.clientserverpaa.net

import com.example.clientserverpaa.utilities.Course
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("posts")
    fun getCourses(): Call<List<Course>>
}