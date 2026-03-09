package com.edudesk.services

import com.edudesk.models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class UserService {
    fun updateProfile(
            userId: Int,
            name: String,
            nimEmail: String,
            email: String,
            password: String? = null
    ): Boolean {
        return try {
            transaction {
                val user = User.findById(userId) ?: return@transaction false
                user.name = name
                user.nim = nimEmail
                user.email = email

                if (!password.isNullOrBlank()) {
                    user.password = BCrypt.hashpw(password, BCrypt.gensalt())
                }
                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
