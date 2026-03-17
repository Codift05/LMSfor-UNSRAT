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
    Lesson,
    Messages,
    AccountSettings,
    Help
}

object NavController {
    var currentScreen by mutableStateOf(Screen.Home)
    var currentLessonIndex by mutableStateOf(0)

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    fun navigateToLesson(index: Int) {
        currentLessonIndex = index
        currentScreen = Screen.Lesson
    }
}
