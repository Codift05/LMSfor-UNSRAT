package com.edudesk.backend.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*

// Users Table
object Users : IntIdTable("users") {
    val name = varchar("name", 255)
    val nim = varchar("nim", 255).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val role = varchar("role", 50)
    val isActive = bool("is_active").default(true)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    var name by Users.name
    var nim by Users.nim
    var email by Users.email
    var password by Users.password
    var role by Users.role
    var isActive by Users.isActive
}

// Courses Table
object Courses : IntIdTable("courses") {
    val name = varchar("name", 255)
    val code = varchar("code", 100)
    val description = text("description")
    val userId = reference("user_id", Users)
    val status = varchar("status", 50).default("pending") // pending, approved, rejected
}

class Course(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Course>(Courses)
    var name by Courses.name
    var code by Courses.code
    var description by Courses.description
    var user by User referencedOn Courses.userId
    var status by Courses.status
}

// Assignments Table
object Assignments : IntIdTable("assignments") {
    val title = varchar("title", 255)
    val description = text("description")
    val deadline = varchar("deadline", 100) 
    val isDone = bool("is_done").default(false)
    val courseId = reference("course_id", Courses)
    val userId = reference("user_id", Users)
}

class Assignment(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Assignment>(Assignments)
    var title by Assignments.title
    var description by Assignments.description
    var deadline by Assignments.deadline
    var isDone by Assignments.isDone
    var course by Course referencedOn Assignments.courseId
    var user by User referencedOn Assignments.userId
}

// Enrollments Table
object Enrollments : IntIdTable("enrollments") {
    val userId = reference("user_id", Users)
    val courseId = reference("course_id", Courses)
    val enrolledAt = varchar("enrolled_at", 100)
}

class Enrollment(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Enrollment>(Enrollments)
    var user by User referencedOn Enrollments.userId
    var course by Course referencedOn Enrollments.courseId
    var enrolledAt by Enrollments.enrolledAt
}
