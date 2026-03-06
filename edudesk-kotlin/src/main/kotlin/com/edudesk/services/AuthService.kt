package com.edudesk.services

import com.edudesk.models.User
import com.edudesk.models.Users
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*

class AuthService {
    
    // Simulates a user login
    fun login(nimEmail: String, pass: String): User? {
        return transaction {
            User.find {
                ((Users.nim eq nimEmail) or (Users.email eq nimEmail)) and (Users.password eq pass)
            }.firstOrNull()
        }
    }
}
