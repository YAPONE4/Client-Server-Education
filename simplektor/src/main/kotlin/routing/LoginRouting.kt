package routing

import cache.InMemoryCache
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.LoginReceiveRemote
import models.TokenResponseRemote

fun Route.LoginRouting() {
    post("/login") {
        val loginReceive = call.receive<LoginReceiveRemote>()

        val user = InMemoryCache.userList.firstOrNull { it.login == loginReceive.login }
        if (user == null) {
            call.respond("User not found")
            return@post
        }

        if (user.password != loginReceive.password) {
            call.respond("Invalid password")
            return@post
        }

        call.respond(TokenResponseRemote(user.token))
    }
}