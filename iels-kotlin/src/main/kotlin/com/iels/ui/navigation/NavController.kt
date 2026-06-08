package com.iels.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Screen {
    Login,
    Home, // Student Dashboard (Enter Token)
    SecureExam, // CBT Engine
    AdminDashboard, // Exam Builder (Legacy name, acts as Builder)
    AdminOverview,  // Dashboard
    AdminMonitoring, // Live Monitoring
    AdminResults,   // Results
    AdminSettings,  // Settings
    AdminUsers,     // User Management
    Register,       // Student Registration
    Landing         // Landing Page
}

object NavController {
    var currentScreen by mutableStateOf(Screen.Landing)
    var activeExamToken by mutableStateOf<String?>(null)

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
    
    fun startExam(token: String) {
        activeExamToken = token
        currentScreen = Screen.SecureExam
    }
}
