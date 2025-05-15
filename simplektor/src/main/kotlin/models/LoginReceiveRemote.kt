package models

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveRemote(val login: String, val password: String)