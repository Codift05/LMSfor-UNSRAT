package com.edudesk.models

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
