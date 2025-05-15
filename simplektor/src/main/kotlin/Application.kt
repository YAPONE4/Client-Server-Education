package com.example.simplektor

import routing.LoginRouting
import routing.RegisterRouting
import routing.TestRoute
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        TestRoute()
        RegisterRouting()
        LoginRouting()
    }
}