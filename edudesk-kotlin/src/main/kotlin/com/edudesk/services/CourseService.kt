package com.edudesk.services

import com.edudesk.models.Course
import com.edudesk.models.Courses
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*

class CourseService {

    fun getCoursesForUser(userId: Int): List<Course> {
        return transaction {
            Course.find { Courses.userId eq userId }.toList()
        }
    }

    fun addCourse(name: String, code: String, desc: String, userId: Int) {
        transaction {
            Course.new {
                this.name = name
                this.code = code
                this.description = desc
                // Getting the User entity reference is needed, or we just pass the ID if we bypass DAO slightly
                // For simplicity assuming User ID matches
                this.user = com.edudesk.models.User[userId]
            }
        }
    }

    fun deleteCourse(courseId: Int) {
        transaction {
            val course = Course.findById(courseId)
            course?.delete()
        }
    }
}
