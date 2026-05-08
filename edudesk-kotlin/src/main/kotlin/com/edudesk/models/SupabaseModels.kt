package com.edudesk.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class SupabaseUser(
    @SerialName("id")
    val id: String,
    @SerialName("nim")
    val nim: String,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("role")
    val role: String,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("created_at")
    val createdAt: String,
)

@Serializable
data class SupabaseCourse(
    @SerialName("id")
    val id: String,
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("instructor_id")
    val instructorId: String,
    @SerialName("credits")
    val credits: Int,
    @SerialName("created_at")
    val createdAt: String,
)

@Serializable
data class SupabaseAssignment(
    @SerialName("id")
    val id: String,
    @SerialName("course_id")
    val courseId: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("due_date")
    val dueDate: String,
    @SerialName("created_at")
    val createdAt: String,
)

@Serializable
data class SupabaseEnrollment(
    @SerialName("id")
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("course_id")
    val courseId: String,
    @SerialName("enrolled_at")
    val enrolledAt: String,
    @SerialName("status")
    val status: String,
)
