package com.edudesk.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Screen {
    Login,
    Home,
    MyLearning,
    Assignments,
    Profile,
    InstructorDashboard,
    AdminDashboard,
    CourseRegistration,
    CourseDetail,
    Messages,
    AccountSettings,
    Help
}

object NavController {
    var currentScreen by mutableStateOf(Screen.Home) // start at home directly for dev
    
    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
}
