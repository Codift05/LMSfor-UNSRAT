package com.edudesk.backend.routes

import com.edudesk.backend.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CourseResponse(
    val id: Int,
    val name: String,
    val code: String,
    val description: String,
    val instructorName: String,
    val status: String
)

@Serializable
data class AddCourseRequest(val name: String, val code: String, val description: String, val userId: Int)

@Serializable
data class CourseStatusRequest(val status: String)

fun Route.courseRoutes() {
    route("/courses") {
        put("/{id}/status") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val request = call.receive<CourseStatusRequest>()
                DatabaseFactory.dbQuery {
                    val course = Course.findById(id)
                    if (course != null) {
                        course.status = request.status
                    }
                }
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            }
        }
        get {
            val userIdParam = call.request.queryParameters["userId"]
            
            val courses = DatabaseFactory.dbQuery {
                if (userIdParam != null) {
                    val userId = userIdParam.toIntOrNull()
                    if (userId != null) {
                        // For simplicity, return all courses created by this user or all courses if admin
                        // A more robust app would join Enrollments, but here we just return all courses
                        Course.all().map {
                            CourseResponse(
                                id = it.id.value,
                                name = it.name,
                                code = it.code,
                                description = it.description,
                                instructorName = it.user.name,
                                status = it.status
                            )
                        }
                    } else {
                        emptyList()
                    }
                } else {
                    Course.all().map {
                        CourseResponse(
                            id = it.id.value,
                            name = it.name,
                            code = it.code,
                            description = it.description,
                            instructorName = it.user.name,
                            status = it.status
                        )
                    }
                }
            }
            call.respond(HttpStatusCode.OK, courses)
        }

        post {
            val request = call.receive<AddCourseRequest>()
            DatabaseFactory.dbQuery {
                Course.new {
                    val u = User.findById(request.userId)
                    if (u != null) {
                        name = request.name
                        code = request.code
                        description = request.description
                        user = u
                    }
                }
            }
            call.respond(HttpStatusCode.Created)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                DatabaseFactory.dbQuery {
                    val course = Course.findById(id)
                    course?.delete()
                }
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            }
        }
    }
}
