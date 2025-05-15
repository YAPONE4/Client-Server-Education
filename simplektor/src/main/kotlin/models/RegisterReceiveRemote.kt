package models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(val login: String, val password: String, val email: String)
