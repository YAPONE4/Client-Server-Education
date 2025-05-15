package routing

import cache.InMemoryCache
import cache.RegisterInfo
import utils.Validators
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.RegisterReceiveRemote
import models.TokenResponseRemote

fun Route.RegisterRouting() {
    post("/register") {
        val registerReceive = call.receive<RegisterReceiveRemote>()

        if (!Validators.isValidEmail(registerReceive.email)) {
            call.respond("Email is not valid")
            return@post
        }

        if (InMemoryCache.userList.any { it.login == registerReceive.login }) {
            call.respond("User already registered")
            return@post
        }

        val token = "${registerReceive.login}_${System.currentTimeMillis()}"
        InMemoryCache.userList.add(
            RegisterInfo(
                login = registerReceive.login,
                password = registerReceive.password,
                email = registerReceive.email,
                token = token
            )
        )

        call.respond(TokenResponseRemote(token))
    }
}