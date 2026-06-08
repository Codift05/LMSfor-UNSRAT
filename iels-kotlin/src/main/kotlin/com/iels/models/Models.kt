package com.iels.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int = 0,
    val name: String = "",
    val nim: String = "",
    val email: String = "",
    val role: String = "student",
    val isActive: Boolean = true
)

@Serializable
data class RegisterRequest(
    val email: String,
    val pass: String,
    val name: String,
    val nim: String,
    val role: String = "student"
)

@Serializable
data class UpdateProfileRequest(
    val name: String,
    val nimEmail: String,
    val email: String,
    val password: String? = null
)

@Serializable
data class Course(
    val id: Int = 0,
    val name: String = "",
    val code: String = "",
    val description: String = "",
    val instructorName: String = "",
    val status: String = "pending"
)

@Serializable
data class Assignment(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val deadline: String = "",
    val isDone: Boolean = false,
    val courseName: String = ""
)

@Serializable
data class AdminStatsDto(
    val totalStudents: Long,
    val averageScore: Double
)
