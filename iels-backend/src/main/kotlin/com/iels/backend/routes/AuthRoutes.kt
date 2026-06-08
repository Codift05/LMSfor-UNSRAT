package com.iels.backend.routes

import com.iels.backend.models.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.or
import org.mindrot.jbcrypt.BCrypt
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val nimEmail: String, val pass: String)

@Serializable
data class RegisterRequest(val email: String, val pass: String, val name: String, val nim: String, val role: String = "student")

@Serializable
data class UserResponse(val id: Int, val name: String, val nim: String, val email: String, val role: String, val isActive: Boolean = true)

fun Route.authRoutes() {
    route("/auth") {
        post("/login") {
            val request = call.receive<LoginRequest>()
            
            val user = DatabaseFactory.dbQuery {
                User.find {
                    (Users.nim eq request.nimEmail) or (Users.email eq request.nimEmail)
                }.firstOrNull()
            }
            
            if (user != null && BCrypt.checkpw(request.pass, user.password)) {
                call.respond(HttpStatusCode.OK, UserResponse(
                    id = user.id.value,
                    name = user.name,
                    nim = user.nim,
                    email = user.email,
                    role = user.role,
                    isActive = user.isActive
                ))
            } else {
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
            }
        }
        
        post("/register") {
            val request = call.receive<RegisterRequest>()
            
            val existing = DatabaseFactory.dbQuery {
                User.find {
                    (Users.nim eq request.nim) or (Users.email eq request.email)
                }.firstOrNull()
            }
            
            if (existing != null) {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to "User already exists"))
                return@post
            }
            
            val newUser = DatabaseFactory.dbQuery {
                User.new {
                    name = request.name
                    nim = request.nim
                    email = request.email
                    password = BCrypt.hashpw(request.pass, BCrypt.gensalt())
                    role = request.role
                }
            }
            
            call.respond(HttpStatusCode.Created, UserResponse(
                id = newUser.id.value,
                name = newUser.name,
                nim = newUser.nim,
                email = newUser.email,
                role = newUser.role,
                isActive = newUser.isActive
            ))
        }
    }
}
