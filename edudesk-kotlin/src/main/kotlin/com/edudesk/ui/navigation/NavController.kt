package com.edudesk.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Screen {
    Login,
    Home,
    MyLearning,
    Assignments,
    Profile
}

object NavController {
    var currentScreen by mutableStateOf(Screen.Login)
    
    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
}
