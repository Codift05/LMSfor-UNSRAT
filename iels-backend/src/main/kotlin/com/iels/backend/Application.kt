package com.iels.backend

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.routing.*
import io.ktor.server.response.*

import com.iels.backend.routes.authRoutes
import com.iels.backend.routes.examRoutes
import com.iels.backend.routes.userRoutes

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    
    // Database initialization
    com.iels.backend.models.DatabaseFactory.init()

    routing {
        authRoutes()
        examRoutes()
        userRoutes()
        
        get("/") {
            call.respondText("Iels Backend API is running")
        }
    }
}
