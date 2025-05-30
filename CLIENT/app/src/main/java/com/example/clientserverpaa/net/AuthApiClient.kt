package com.example.clientserverpaa.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: AuthApiService = retrofit.create(AuthApiService::class.java)
}