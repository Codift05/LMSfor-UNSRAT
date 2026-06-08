package com.iels.models

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int? = null,
    val text: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String = ""
)

@Serializable
data class Exam(
    val id: Int? = null,
    val title: String,
    val durationMinutes: Int,
    val token: String,
    val instructorId: Int,
    val questions: List<Question> = emptyList()
)

@Serializable
data class ExamSubmission(
    val userId: Int,
    val examId: Int,
    val answers: Map<Int, String>,
    val cheatCount: Int
)

@Serializable
data class ExamResult(
    val score: Int,
    val cheatCount: Int
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

@Serializable
data class StartSessionDto(
    val userId: Int,
    val examId: Int
)
