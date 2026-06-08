package com.iels.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.iels.models.User

object SessionManager {
    var currentUser: User? by mutableStateOf(null)
    
    var isLoggedIn: Boolean by mutableStateOf(false)
    var isLoading: Boolean by mutableStateOf(false)
    var errorMessage: String? by mutableStateOf(null)
    
    fun setLocalUser(user: User?) {
        currentUser = user
        isLoggedIn = user != null
    }
    
    fun logout() {
        currentUser = null
        isLoggedIn = false
        errorMessage = null
    }
    
    fun setError(error: String?) {
        errorMessage = error
    }
    
    fun getCurrentUserEmail(): String? {
        return currentUser?.email
    }
    
    fun getCurrentUserName(): String? {
        return currentUser?.name
    }
}
