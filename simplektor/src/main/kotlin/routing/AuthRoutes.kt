package com.example.routing

import com.example.login.LoginController
import com.example.models.*
import com.example.register.RegisterController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {

        // маленький тест «живой ли сервер»
        get("/test") { call.respond(MessageResponse("OK")) }

        /* ---- REGISTER ---- */
        post("/register") {
            val receive = call.receive<RegisterReceiveRemote>()
            val result  = RegisterController().registerNewUser(receive)

            result.fold(
                onSuccess = { call.respond(HttpStatusCode.Created, it) },
                onFailure = { call.respond(HttpStatusCode.BadRequest, MessageResponse(it.message ?: "")) }
            )
        }

        /* ---- LOGIN ---- */
        post("/login") {
            val receive = call.receive<LoginReceiveRemote>()
            val result  = LoginController().performLogin(receive)

            result.fold(
                onSuccess = { call.respond(HttpStatusCode.OK, it) },
                onFailure = { call.respond(HttpStatusCode.BadRequest, MessageResponse(it.message ?: "")) }
            )
        }
    }
}