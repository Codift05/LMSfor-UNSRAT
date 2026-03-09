package com.edudesk.services

import com.edudesk.models.User
import com.edudesk.models.Users
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.mindrot.jbcrypt.BCrypt

class AuthService {
    
    // Verifies user login using BCrypt
    fun login(nimEmail: String, pass: String): User? {
        return transaction {
            val user = User.find {
                (Users.nim eq nimEmail) or (Users.email eq nimEmail)
            }.firstOrNull()

            if (user != null && BCrypt.checkpw(pass, user.password)) {
                user
            } else {
                null
            }
        }
    }
}
