package com.example.clientserverpaa.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: JsonPlaceholderApi = retrofit.create(JsonPlaceholderApi::class.java)
}