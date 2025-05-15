package cache

object InMemoryCache {
    val userList = mutableListOf<RegisterInfo>()
}

data class RegisterInfo(val login: String, val password: String, val email: String, val token: String)