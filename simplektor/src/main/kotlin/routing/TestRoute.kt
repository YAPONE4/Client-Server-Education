package routing

import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.TestRoute() {
    get("/test") {
        call.respond(TestResponse("Hello from server"))
    }
}

@Serializable
data class TestResponse(val text: String)