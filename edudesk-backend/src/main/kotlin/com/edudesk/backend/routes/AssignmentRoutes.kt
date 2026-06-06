package com.edudesk.backend.routes

import com.edudesk.backend.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class AssignmentResponse(
    val id: Int,
    val title: String,
    val description: String,
    val deadline: String,
    val isDone: Boolean,
    val courseName: String
)

@Serializable
data class AddAssignmentRequest(val title: String, val desc: String, val deadline: String, val courseId: Int, val userId: Int)

fun Route.assignmentRoutes() {
    route("/assignments") {
        get {
            val userIdParam = call.request.queryParameters["userId"]
            
            val assignments = DatabaseFactory.dbQuery {
                if (userIdParam != null) {
                    val userId = userIdParam.toIntOrNull()
                    if (userId != null) {
                        Assignment.all().map {
                            AssignmentResponse(
                                id = it.id.value,
                                title = it.title,
                                description = it.description,
                                deadline = it.deadline,
                                isDone = it.isDone,
                                courseName = it.course.name
                            )
                        }
                    } else {
                        emptyList()
                    }
                } else {
                    Assignment.all().map {
                        AssignmentResponse(
                            id = it.id.value,
                            title = it.title,
                            description = it.description,
                            deadline = it.deadline,
                            isDone = it.isDone,
                            courseName = it.course.name
                        )
                    }
                }
            }
            call.respond(HttpStatusCode.OK, assignments)
        }

        post {
            val request = call.receive<AddAssignmentRequest>()
            DatabaseFactory.dbQuery {
                Assignment.new {
                    val c = Course.findById(request.courseId)
                    val u = User.findById(request.userId)
                    if (c != null && u != null) {
                        title = request.title
                        description = request.desc
                        deadline = request.deadline
                        isDone = false
                        course = c
                        user = u
                    }
                }
            }
            call.respond(HttpStatusCode.Created)
        }

        put("/{id}/done") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                DatabaseFactory.dbQuery {
                    val assignment = Assignment.findById(id)
                    assignment?.isDone = true
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
                    val assignment = Assignment.findById(id)
                    assignment?.delete()
                }
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            }
        }
    }
}
