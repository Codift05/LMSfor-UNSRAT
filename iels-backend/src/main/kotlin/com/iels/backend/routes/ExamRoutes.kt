package com.iels.backend.routes

import com.iels.backend.models.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.and
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val id: Int? = null,
    val text: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String
)

@Serializable
data class ExamDto(
    val id: Int? = null,
    val title: String,
    val durationMinutes: Int,
    val token: String,
    val instructorId: Int,
    val questions: List<QuestionDto> = emptyList()
)

@Serializable
data class ExamSubmissionDto(
    val userId: Int,
    val examId: Int,
    val answers: Map<Int, String>, // Question ID -> Selected Option
    val cheatCount: Int
)

@Serializable
data class ExamResultDto(
    val score: Int,
    val cheatCount: Int
)

@Serializable
data class StartSessionDto(
    val userId: Int,
    val examId: Int
)

@Serializable
data class AdminStatsDto(
    val totalStudents: Long,
    val averageScore: Double
)

@Serializable
data class ExamSessionDto(
    val id: Int,
    val userId: Int,
    val userName: String,
    val userNim: String,
    val examId: Int,
    val examTitle: String,
    val startTime: String,
    val status: String
)

@Serializable
data class ExamResultDetailDto(
    val id: Int,
    val userId: Int,
    val userName: String,
    val userNim: String,
    val examId: Int,
    val examTitle: String,
    val score: Int,
    val cheatCount: Int,
    val submittedAt: String
)

fun Route.examRoutes() {
    route("/exams") {
        
        // Get admin stats
        get("/stats") {
            val stats = DatabaseFactory.dbQuery {
                val studentCount = User.find { Users.role eq "student" }.count()
                
                val allResults = ExamResult.all().toList()
                val average = if (allResults.isNotEmpty()) {
                    allResults.map { it.score }.average()
                } else {
                    0.0
                }
                
                AdminStatsDto(totalStudents = studentCount, averageScore = average)
            }
            call.respond(stats)
        }

        // Get all active exams
        get {
            val exams = DatabaseFactory.dbQuery {
                Exam.all().map { exam ->
                    ExamDto(
                        id = exam.id.value,
                        title = exam.title,
                        durationMinutes = exam.durationMinutes,
                        token = exam.token,
                        instructorId = exam.instructor.id.value
                    )
                }
            }
            call.respond(exams)
        }
        
        // Create an exam
        post {
            val request = call.receive<ExamDto>()
            val newExam = DatabaseFactory.dbQuery {
                val instructor = User.findById(request.instructorId) ?: throw Exception("Instructor not found")
                val exam = Exam.new {
                    title = request.title
                    durationMinutes = request.durationMinutes
                    token = request.token
                    this.instructor = instructor
                }
                
                request.questions.forEach { q ->
                    Question.new {
                        this.exam = exam
                        text = q.text
                        optionA = q.optionA
                        optionB = q.optionB
                        optionC = q.optionC
                        optionD = q.optionD
                        correctAnswer = q.correctAnswer
                    }
                }
                exam
            }
            call.respond(HttpStatusCode.Created, mapOf("id" to newExam.id.value))
        }

        // Get an exam by token (for students to start)
        get("/token/{token}") {
            val token = call.parameters["token"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            
            val exam = DatabaseFactory.dbQuery {
                Exam.find { Exams.token eq token }.firstOrNull()?.let { e ->
                    val questions = Question.find { Questions.examId eq e.id }.map { q ->
                        QuestionDto(
                            id = q.id.value,
                            text = q.text,
                            optionA = q.optionA,
                            optionB = q.optionB,
                            optionC = q.optionC,
                            optionD = q.optionD,
                            correctAnswer = "" // Don't send the answer to the client!
                        )
                    }
                    ExamDto(
                        id = e.id.value,
                        title = e.title,
                        durationMinutes = e.durationMinutes,
                        token = e.token,
                        instructorId = e.instructor.id.value,
                        questions = questions
                    )
                }
            }
            
            if (exam != null) {
                call.respond(exam)
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Exam not found or invalid token"))
            }
        }
        
        // Start a session (Live monitoring)
        post("/start-session") {
            val req = call.receive<StartSessionDto>()
            DatabaseFactory.dbQuery {
                val user = User.findById(req.userId) ?: throw Exception("User not found")
                val exam = Exam.findById(req.examId) ?: throw Exception("Exam not found")
                
                // Check if session already exists
                val existing = ExamSession.find { (ExamSessions.userId eq user.id) and (ExamSessions.examId eq exam.id) }.firstOrNull()
                if (existing != null) {
                    existing.status = "active"
                    existing.startTime = System.currentTimeMillis().toString()
                } else {
                    ExamSession.new {
                        this.user = user
                        this.exam = exam
                        this.startTime = System.currentTimeMillis().toString()
                        this.status = "active"
                    }
                }
            }
            call.respond(HttpStatusCode.OK, mapOf("status" to "started"))
        }

        // Get active sessions
        get("/sessions") {
            val sessions = DatabaseFactory.dbQuery {
                ExamSession.find { ExamSessions.status eq "active" }.map { s ->
                    ExamSessionDto(
                        id = s.id.value,
                        userId = s.user.id.value,
                        userName = s.user.name,
                        userNim = s.user.nim,
                        examId = s.exam.id.value,
                        examTitle = s.exam.title,
                        startTime = s.startTime,
                        status = s.status
                    )
                }
            }
            call.respond(sessions)
        }

        // Get all exam results (for ranking)
        get("/results") {
            val results = DatabaseFactory.dbQuery {
                ExamResult.all().map { r ->
                    ExamResultDetailDto(
                        id = r.id.value,
                        userId = r.user.id.value,
                        userName = r.user.name,
                        userNim = r.user.nim,
                        examId = r.exam.id.value,
                        examTitle = r.exam.title,
                        score = r.score,
                        cheatCount = r.cheatCount,
                        submittedAt = r.submittedAt
                    )
                }.sortedByDescending { it.score }
            }
            call.respond(results)
        }
        
        // Submit an exam
        post("/submit") {
            val submission = call.receive<ExamSubmissionDto>()
            
            val result = DatabaseFactory.dbQuery {
                val exam = Exam.findById(submission.examId) ?: throw Exception("Exam not found")
                val user = User.findById(submission.userId) ?: throw Exception("User not found")
                
                val questions = Question.find { Questions.examId eq exam.id }.toList()
                var correctCount = 0
                
                questions.forEach { q ->
                    val userAnswer = submission.answers[q.id.value]
                    if (userAnswer == q.correctAnswer) {
                        correctCount++
                    }
                }
                
                val finalScore = if (questions.isNotEmpty()) (correctCount * 100) / questions.size else 0
                
                val resultRecord = ExamResult.new {
                    this.user = user
                    this.exam = exam
                    score = finalScore
                    cheatCount = submission.cheatCount
                    submittedAt = System.currentTimeMillis().toString()
                }
                
                // Update session to finished
                val session = ExamSession.find { (ExamSessions.userId eq user.id) and (ExamSessions.examId eq exam.id) }.firstOrNull()
                session?.status = "finished"
                
                ExamResultDto(score = finalScore, cheatCount = submission.cheatCount)
            }
            
            call.respond(HttpStatusCode.OK, result)
        }
    }
}
