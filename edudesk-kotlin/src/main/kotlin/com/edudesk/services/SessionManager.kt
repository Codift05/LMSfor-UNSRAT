package com.edudesk.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.edudesk.models.User

object SessionManager {
    var currentUser: User? by mutableStateOf(null)
}
