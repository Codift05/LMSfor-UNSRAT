package com.iels.services

import com.iels.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ExamService {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
    
    private val baseUrl = "http://localhost:8081/exams"

    suspend fun getExams(): List<Exam> {
        return try {
            val response = client.get(baseUrl)
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println("Error fetching exams: ${e.message}")
            emptyList()
        }
    }

    suspend fun getAdminStats(): AdminStatsDto? {
        return try {
            val response = client.get("$baseUrl/stats")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error fetching admin stats: ${e.message}")
            null
        }
    }

    suspend fun getExamByToken(token: String): Exam? {
        return try {
            val response = client.get("$baseUrl/token/$token")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error fetching exam by token: ${e.message}")
            null
        }
    }

    suspend fun createExam(exam: Exam): Boolean {
        return try {
            val response = client.post(baseUrl) {
                contentType(ContentType.Application.Json)
                setBody(exam)
            }
            response.status == HttpStatusCode.Created
        } catch (e: Exception) {
            println("Error creating exam: ${e.message}")
            false
        }
    }

    suspend fun deleteExam(examId: Int): Boolean {
        return try {
            val response = client.delete("$baseUrl/$examId")
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            println("Error deleting exam: ${e.message}")
            false
        }
    }

    suspend fun submitExam(submission: ExamSubmission): ExamResult? {
        return try {
            val response = client.post("$baseUrl/submit") {
                contentType(ContentType.Application.Json)
                setBody(submission)
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error submitting exam: ${e.message}")
            null
        }
    }

    suspend fun startSession(dto: StartSessionDto): Boolean {
        return try {
            val response = client.post("$baseUrl/start-session") {
                contentType(ContentType.Application.Json)
                setBody(dto)
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            println("Error starting session: ${e.message}")
            false
        }
    }

    suspend fun getSessions(): List<ExamSessionDto> {
        return try {
            val response = client.get("$baseUrl/sessions")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println("Error fetching sessions: ${e.message}")
            emptyList()
        }
    }

    suspend fun getResults(): List<ExamResultDetailDto> {
        return try {
            val response = client.get("$baseUrl/results")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println("Error fetching results: ${e.message}")
            emptyList()
        }
    }
}
