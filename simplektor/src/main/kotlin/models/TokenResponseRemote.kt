package models

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseRemote(val token: String)