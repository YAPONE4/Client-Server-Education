package com.example.login

import com.example.DatabaseFactory.dbQuery
import com.example.models.*
import org.jetbrains.exposed.sql.insert
import java.util.*

class LoginController {

    suspend fun performLogin(receive: LoginReceiveRemote): Result<TokenResponseRemote> =
        dbQuery {
            val user = Users.fetchUser(receive.email)
                ?: return@dbQuery Result.failure(IllegalArgumentException("User not found"))

            if (user.passwordHash != receive.password) {
                Result.failure(IllegalArgumentException("Invalid password"))
            } else {
                // генерим новый токен каждый раз
                val token = UUID.randomUUID().toString()
                Tokens.insertToken(receive.email, token)
                Result.success(TokenResponseRemote(token))
            }
        }
}