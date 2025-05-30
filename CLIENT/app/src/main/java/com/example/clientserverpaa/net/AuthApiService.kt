package com.example.clientserverpaa.net

import com.example.clientserverpaa.utilities.LoginRequest
import com.example.clientserverpaa.utilities.RegisterRequest
import com.example.clientserverpaa.utilities.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponse>
}