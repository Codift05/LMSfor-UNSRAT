package com.edudesk.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.edudesk.models.User
import com.edudesk.models.SupabaseUser

object SessionManager {
    // Local SQLite user
    var currentUser: User? by mutableStateOf(null)
    
    // Supabase user
    var currentSupabaseUser: SupabaseUser? by mutableStateOf(null)
    
    // Session state
    var isLoggedIn: Boolean by mutableStateOf(false)
    var isLoading: Boolean by mutableStateOf(false)
    var errorMessage: String? by mutableStateOf(null)
    
    fun setLocalUser(user: User?) {
        currentUser = user
        isLoggedIn = user != null
        if (user == null) {
            currentSupabaseUser = null
        }
    }
    
    fun setSupabaseUser(user: SupabaseUser?) {
        currentSupabaseUser = user
        isLoggedIn = user != null
        if (user == null) {
            currentUser = null
        }
    }
    
    fun logout() {
        currentUser = null
        currentSupabaseUser = null
        isLoggedIn = false
        errorMessage = null
    }
    
    fun setError(error: String?) {
        errorMessage = error
    }
    
    fun getCurrentUserEmail(): String? {
        return currentSupabaseUser?.email ?: currentUser?.email
    }
    
    fun getCurrentUserName(): String? {
        return currentSupabaseUser?.name ?: currentUser?.name
    }
}
