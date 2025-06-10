package com.example.register

import com.example.DatabaseFactory.dbQuery
import com.example.models.*
import com.example.utils.isEmailValid
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import java.util.*

class RegisterController {

    suspend fun registerNewUser(receive: RegisterReceiveRemote): Result<TokenResponseRemote> =
        dbQuery {
            when {
                !isEmailValid(receive.email) ->
                    Result.failure(IllegalArgumentException("Email is not valid"))

                Users.fetchUser(receive.email) != null ->
                    Result.failure(IllegalArgumentException("User already registered"))

                else -> {
                    val token = UUID.randomUUID().toString()

                    val userId = Users.insertAndGetId {
                        it[email] = receive.email
                        it[passwordHash] = receive.password // TODO: hash
                    }.value

                    Tokens.insertToken(receive.email, token)

                    Result.success(TokenResponseRemote(token, userId))
                }
            }
        }
}