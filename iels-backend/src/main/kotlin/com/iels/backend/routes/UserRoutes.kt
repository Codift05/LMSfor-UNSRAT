package com.iels.backend.routes

import com.iels.backend.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UpdateProfileRequest(
    val name: String,
    val nimEmail: String, // mapped to nim in our DB
    val email: String,
    val password: String? = null
)

@Serializable
data class UserStatusRequest(val isActive: Boolean)

fun Route.userRoutes() {
    route("/users") {
        get {
            val users = DatabaseFactory.dbQuery {
                User.all().map {
                    UserResponse(
                        id = it.id.value,
                        name = it.name,
                        nim = it.nim,
                        email = it.email,
                        role = it.role,
                        isActive = it.isActive
                    )
                }
            }
            call.respond(HttpStatusCode.OK, users)
        }

        put("/{id}/status") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val request = call.receive<UserStatusRequest>()
                DatabaseFactory.dbQuery {
                    val user = User.findById(id)
                    if (user != null) {
                        user.isActive = request.isActive
                    }
                }
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val request = call.receive<UpdateProfileRequest>()
                DatabaseFactory.dbQuery {
                    val user = User.findById(id)
                    if (user != null) {
                        user.name = request.name
                        user.nim = request.nimEmail
                        user.email = request.email
                        if (!request.password.isNullOrBlank()) {
                            user.password = BCrypt.hashpw(request.password, BCrypt.gensalt())
                        }
                    }
                }
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                DatabaseFactory.dbQuery {
                    val user = User.findById(id)
                    user?.delete()
                }
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            }
        }
    }
}
