package com.example.clientserverpaa.utilities

data class RegisterRequest(
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class TokenResponse(
    val token: String
)

data class ErrorResponse(
    val error: String
)