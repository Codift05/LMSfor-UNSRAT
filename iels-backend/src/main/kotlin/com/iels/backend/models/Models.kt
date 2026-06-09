package com.iels.backend.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*

// Users Table (Kept as is)
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

// Exams Table (New)
object Exams : IntIdTable("exams") {
    val title = varchar("title", 255)
    val category = varchar("category", 100).default("Umum")
    val durationMinutes = integer("duration_minutes")
    val token = varchar("token", 50).uniqueIndex()
    val instructorId = reference("instructor_id", Users)
    val isActive = bool("is_active").default(true)
}

class Exam(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Exam>(Exams)
    var title by Exams.title
    var category by Exams.category
    var durationMinutes by Exams.durationMinutes
    var token by Exams.token
    var instructor by User referencedOn Exams.instructorId
    var isActive by Exams.isActive
}

// Questions Table (New)
object Questions : IntIdTable("questions") {
    val examId = reference("exam_id", Exams)
    val text = text("text")
    val optionA = varchar("option_a", 255)
    val optionB = varchar("option_b", 255)
    val optionC = varchar("option_c", 255)
    val optionD = varchar("option_d", 255)
    val correctAnswer = varchar("correct_answer", 1) // 'A', 'B', 'C', or 'D'
}

class Question(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Question>(Questions)
    var exam by Exam referencedOn Questions.examId
    var text by Questions.text
    var optionA by Questions.optionA
    var optionB by Questions.optionB
    var optionC by Questions.optionC
    var optionD by Questions.optionD
    var correctAnswer by Questions.correctAnswer
}

// ExamResults Table (New)
object ExamResults : IntIdTable("exam_results") {
    val userId = reference("user_id", Users)
    val examId = reference("exam_id", Exams)
    val score = integer("score")
    val cheatCount = integer("cheat_count").default(0) // Number of focus losses
    val submittedAt = varchar("submitted_at", 100)
}

class ExamResult(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExamResult>(ExamResults)
    var user by User referencedOn ExamResults.userId
    var exam by Exam referencedOn ExamResults.examId
    var score by ExamResults.score
    var cheatCount by ExamResults.cheatCount
    var submittedAt by ExamResults.submittedAt
}

// ExamSessions Table (New)
object ExamSessions : IntIdTable("exam_sessions") {
    val userId = reference("user_id", Users)
    val examId = reference("exam_id", Exams)
    val startTime = varchar("start_time", 100)
    val status = varchar("status", 50).default("active") // active or finished
}

class ExamSession(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExamSession>(ExamSessions)
    var user by User referencedOn ExamSessions.userId
    var exam by Exam referencedOn ExamSessions.examId
    var startTime by ExamSessions.startTime
    var status by ExamSessions.status
}
