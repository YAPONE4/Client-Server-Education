package com.example.models

@kotlinx.serialization.Serializable
data class UserDTO(val email: String, val passwordHash: String)

@kotlinx.serialization.Serializable
data class RegisterReceiveRemote(val email: String, val password: String)

@kotlinx.serialization.Serializable
data class LoginReceiveRemote(val email: String, val password: String)

@kotlinx.serialization.Serializable
data class TokenResponseRemote(val token: String)

@kotlinx.serialization.Serializable
data class MessageResponse(val message: String)